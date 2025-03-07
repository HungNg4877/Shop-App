package burundi.ilucky.service.GiftService;

import burundi.ilucky.model.Gift;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GiftService {


    private static final List<Gift> weightedGiftList = new ArrayList<>();
    private static final Map<String, Gift> giftMap = new HashMap<>();

    static {
        // Hàm hỗ trợ thêm quà theo tỉ lệ
        addGift("10000VND", "10.000 VND", 10000, "VND", 1);
        addGift("1000VND", "1.000 VND", 1000, "VND", 2);
        addGift("500VND", "500 VND", 500, "VND", 3);
        addGift("200VND", "200 VND", 200, "VND", 5);

        addGift("SAMSUNG1", "Mảnh Samsung 1", 1, "SAMSUNG", 7);
        addGift("SAMSUNG2", "Mảnh Samsung 2", 1, "SAMSUNG", 7);
        addGift("SAMSUNG3", "Mảnh Samsung 3", 1, "SAMSUNG", 5);
        addGift("SAMSUNG4", "Mảnh Samsung 4", 1, "SAMSUNG", 7);

        addGift("L", "1 Chữ cái \"L\"", 1, "PIECE", 5);
        addGift("I", "1 Chữ cái \"I\"", 1, "PIECE", 2);
        addGift("T", "1 Chữ cái \"T\"", 1, "PIECE", 5);
        addGift("E", "1 Chữ cái \"E\"", 1, "PIECE", 5);

        addGift("SHARE", "Chia sẻ để nhận 1 lượt chơi", 1, "SHARE", 8);

        addGift("5STARS", "5 Sao", 1, "STARS", 10);
        addGift("55STARS", "55 Sao", 2, "STARS", 8);
        addGift("555STARS", "555 Sao", 3, "STARS", 6);
        addGift("5555STARS", "5555 Sao", 4, "STARS", 5);

        addGift("UNLUCKY", "Chúc bạn may mắn lần sau", 1, "UNLUCKY", 9);
    }

    private static void addGift(String id, String name, int value, String type, int probability) {
        Gift gift = new Gift(id, name, value, type,probability);
        giftMap.put(id, gift);
        for (int i = 0; i < probability; i++) {
            weightedGiftList.add(gift);
        }
    }

    public static Gift getRandomGift() {
        Random random = new Random();
        return weightedGiftList.get(random.nextInt(weightedGiftList.size()));
    }
    public static Gift getGiftById(String giftId) {
        return giftMap.get(giftId);
    }
    public static Collection<Gift> getAllGifts() {
        return giftMap.values();
    }
}
