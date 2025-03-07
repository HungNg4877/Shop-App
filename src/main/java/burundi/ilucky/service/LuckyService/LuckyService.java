package burundi.ilucky.service.LuckyService;

import burundi.ilucky.model.BonusHistory;
import burundi.ilucky.model.Gift;
import burundi.ilucky.model.LuckyHistory;
import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.LuckyHistoryDTO;
import burundi.ilucky.payload.Request.PurchaseRequest;
import burundi.ilucky.payload.Response.PurchaseResponse;
import burundi.ilucky.repository.BonusHistoryRepository;
import burundi.ilucky.repository.LuckyHistoryRepository;
import burundi.ilucky.repository.UserRepository;
import burundi.ilucky.service.GiftService.GiftService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class LuckyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LuckyHistoryRepository luckyHistoryRepository;

    @Autowired
    private BonusHistoryRepository bonusHistoryRepository;

    public List<LuckyHistory> getHistoriesByUserId(Long userId) {
        return luckyHistoryRepository.findByUserIdOrderByAddTimeDesc(userId);
    }

    public Gift lucky(User user) {
        Gift gift = GiftService.getRandomGift();

        LuckyHistory luckyHistory = new LuckyHistory();
        if(gift.getType().equals("VND")) {
            user.setTotalVnd(user.getTotalVnd() + gift.getNoItem());
        } else if(gift.getType().equals("STARS")) {
            user.setTotalStar(user.getTotalStar() + gift.getNoItem());
        }

        luckyHistory.setGiftType(gift.getType());
        luckyHistory.setAddTime(new Date());
        luckyHistory.setGiftId(gift.getId());
        luckyHistory.setNoItem(gift.getNoItem());
        luckyHistory.setUser(user);
        luckyHistoryRepository.save(luckyHistory);

        user.setTotalPlay(user.getTotalPlay() - 1);
        userRepository.save(user);

        return gift;
    }
    public Gift lucky() {
        Gift gift = GiftService.getRandomGift();

        LuckyHistory luckyHistory = new LuckyHistory();
        luckyHistory.setGiftType(gift.getType());
        luckyHistory.setAddTime(new Date());
        luckyHistory.setGiftId(gift.getId());
        luckyHistory.setNoItem(gift.getNoItem());
        luckyHistoryRepository.save(luckyHistory);

        return gift;
    }
    public List<LuckyHistoryDTO> convertLuckyHistoriesToDTO(List<LuckyHistory> luckyGiftHistories) {
    	List<LuckyHistoryDTO> luckyGiftHistoriesDTO = new ArrayList<>();
    	
    	for(LuckyHistory item: luckyGiftHistories) {
    		Gift gift = GiftService.getGiftById(item.getGiftId());
    		LuckyHistoryDTO luckyHistoryDTO = new LuckyHistoryDTO(item.getAddTime(), gift);
    		luckyGiftHistoriesDTO.add(luckyHistoryDTO);
    	}
    	
    	return luckyGiftHistoriesDTO;
    }

    // Giá tiền mỗi lượt chơi (ví dụ: 10,000 VND/lượt)
    private static final long PRICE_PER_PLAY = 10000;

    public PurchaseResponse purchasePlay(String username, PurchaseRequest request) {
        User user = userRepository.findByUsername(username);
        long quantity = request.getQuantity();
        long totalCost = quantity * PRICE_PER_PLAY;


        if (user.getTotalVnd() < totalCost) {
            return new PurchaseResponse("Số dư không đủ để mua lượt chơi.", user.getTotalPlay(), user.getTotalVnd());
        }

        user.setTotalVnd(user.getTotalVnd() - totalCost);
        user.setTotalPlay(user.getTotalPlay() + quantity);
        userRepository.save(user);

        return new PurchaseResponse("Mua thành công " + quantity + " lượt chơi.", user.getTotalPlay(), user.getTotalVnd());
    }
    @Scheduled(cron = "0 0 0 * * ?") // Chạy vào 00:00 mỗi ngày
//    @Scheduled(cron = "0 * * * * ?") // Chạy mỗi phút
    public void addFreePlays() {
        log.info("Bắt đầu quá trình tặng 5 lượt chơi miễn phí cho tất cả người dùng...");

        // Lấy tất cả người dùng từ database
        List<User> users = userRepository.findAll();

        for (User user : users) {
            user.setTotalPlay(user.getTotalPlay() + 5);
            userRepository.save(user);
            BonusHistory history = new BonusHistory();
            history.setUser(user);
            history.setBonusAmount(5);
            history.setBonusTime(new Date());
            bonusHistoryRepository.save(history);
        }

        // Lưu tất cả thay đổi vào database
        userRepository.saveAll(users);

        log.info("Đã tặng thành công 5 lượt chơi miễn phí cho {} người dùng.", users.size());
    }
}
