	package burundi.ilucky.controller;

import burundi.ilucky.model.User;
import burundi.ilucky.model.dto.UserDTO;
import burundi.ilucky.payload.Request.PagingRequest;
import burundi.ilucky.payload.Response.APIResponse;
import burundi.ilucky.payload.Response.PageResponse;
import burundi.ilucky.service.UserService.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

    @RestController
@Log4j2
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/info")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            User user = userService.findByUserName(userDetails.getUsername());
            UserDTO userDTO = new UserDTO(user);
            return ResponseEntity.ok(userDTO);
        } catch (Exception e) {
            log.warn(e);
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/leaderboard-star")
    public ResponseEntity<APIResponse<PageResponse<UserDTO>>> getLeaderboard(@RequestBody PagingRequest<User> pagingRequest) {
            PageResponse<UserDTO> response = userService.getTopUsersByStars(pagingRequest);
            return ResponseEntity.ok(new APIResponse<>(HttpStatus.OK.value(), "success",response));
        }


}
