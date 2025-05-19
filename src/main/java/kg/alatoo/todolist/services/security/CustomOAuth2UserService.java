package kg.alatoo.todolist.services.security;

import kg.alatoo.todolist.entities.Role;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.UserRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
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
            String token = userRequest.getAccessToken().getTokenValue();

            try {
                List<Map<String, Object>> emails = WebClient.create()
                        .get()
                        .uri("https://api.github.com/user/emails")
                        .headers(h -> h.setBearerAuth(token))
                        .retrieve()
                        .bodyToFlux(new ParameterizedTypeReference<Map<String, Object>>() {})
                        .collectList()
                        .block();

                if (emails != null) {
                    for (Map<String, Object> mail : emails) {
                        if (Boolean.TRUE.equals(mail.get("primary")) && Boolean.TRUE.equals(mail.get("verified"))) {
                            email = (String) mail.get("email");
                            break;
                        }
                    }
                }

            } catch (WebClientResponseException e) {
                throw new OAuth2AuthenticationException("Ошибка получения email из GitHub: " + e.getMessage());
            }

            if (email == null) {
                throw new OAuth2AuthenticationException("Email не найден у OAuth-пользователя");
            }
        }

        String finalEmail = email;
        userRepository.findByEmail(finalEmail).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(finalEmail);
            newUser.setName((String) attributes.getOrDefault("name", finalEmail.split("@")[0]));
            newUser.setRole(Role.USER);
            return userRepository.save(newUser);
        });

        String usernameAttribute = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();

        return new DefaultOAuth2User(
                oauthUser.getAuthorities(),
                attributes,
                usernameAttribute
        );
    }
}
