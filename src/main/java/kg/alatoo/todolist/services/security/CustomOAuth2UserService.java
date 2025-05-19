package kg.alatoo.todolist.services.security;

import kg.alatoo.todolist.entities.Role;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public CustomOAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauthUser = super.loadUser(userRequest);
        Map<String, Object> attributes = oauthUser.getAttributes();

        String provider = userRequest.getClientRegistration().getRegistrationId();
        String email = (String) attributes.get("email");

        if (email == null && provider.equals("github")) {
            throw new OAuth2AuthenticationException("Email не найден у OAuth-пользователя");
        }

        userRepository.findByEmail(email).orElseGet(() -> {
            User user = new User();
            user.setEmail(email);
            user.setName((String) attributes.getOrDefault("name", email.split("@")[0]));
            user.setRole(Role.USER);
            user.setPassword("");
            return userRepository.save(user);
        });

        return new DefaultOAuth2User(
                oauthUser.getAuthorities(),
                attributes,
                userRequest.getClientRegistration().getProviderDetails()
                        .getUserInfoEndpoint().getUserNameAttributeName()
        );
    }

}
