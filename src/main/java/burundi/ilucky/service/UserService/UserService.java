package burundi.ilucky.service.UserService;

import burundi.ilucky.Util.PageUtil;
import burundi.ilucky.model.dto.UserDTO;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import burundi.ilucky.model.User;
import burundi.ilucky.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;


	public User findByUserName(String username) {
		try {
			return userRepository.findByUsername(username);
		} catch (Exception e) {
			return null;
		}
	}
	public PageResponse<UserDTO> getTopUsersByStars(PagingRequest<User> pagingRequest) {
		// Lấy đối tượng Pageable từ PageUtil
		Pageable pageable = PageUtil.getPageRequest(pagingRequest);
		Page<User> users = userRepository.findAllByOrderByTotalStarDesc(pageable);
		List<UserDTO> userDTOs = users.getContent().stream()
				.map(user -> new UserDTO(user))
				.collect(Collectors.toList());

		return new PageResponse<>(new PageImpl<>(userDTOs, pageable, users.getTotalElements()));

	}


	public User saveUser(User user) {
		return userRepository.save(user);
	}


}
