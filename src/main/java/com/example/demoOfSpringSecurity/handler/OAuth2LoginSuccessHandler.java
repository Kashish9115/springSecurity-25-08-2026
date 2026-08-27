package com.example.demoOfSpringSecurity.handler;

import com.example.demoOfSpringSecurity.customJwt.JwtService;
import com.example.demoOfSpringSecurity.entity.NewUserrr;
import com.example.demoOfSpringSecurity.repo.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

   private  static  final String PROVIDER ="GITHUB";


   private  final UserRepo repo;

   private  final JwtService jwtService;

   private  final PasswordEncoder passwordEncoder;




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
      OAuth2User oAuth2User= (OAuth2User) authentication.getPrincipal(); // currently authenticated user
        Object id = oAuth2User.getAttribute("id");
        String providerId= String.valueOf(id);

        String login=oAuth2User.getAttribute("login");//username
        String email=oAuth2User.getAttribute("email");// email

        if(email==null)// if email is not provided by provider /or not public
            email+=providerId+"+"+login+"@users.noreply.github.com"; // create a unique email to identify the user

         NewUserrr appUser=findOrCreate(providerId, login,email);

         String token = jwtService.generateToken(appUser.getEmail(), appUser.getRole());

         response.setContentType(MediaType.APPLICATION_JSON_VALUE);

         response.getWriter().write(
                 """
                         {
                         "token": "%s",
                         "email":"%s"
                         
                         }
                         """.formatted(token,appUser.getEmail())

         );
        }






    private  NewUserrr findOrCreate(String providerId, String login, String email){
        return  repo.findByProviderAndProviderId(PROVIDER, providerId).orElseGet(()
                        ->repo.findByEmail(email)
                        .map(existing->{
                            existing.setProviderId(providerId);
                            existing.setProvider(PROVIDER);
                            return repo.save(existing);
                        }).orElseGet(()->repo.save(NewUserrr.builder().username(login).
                        email(email).role("USER").
                        password(passwordEncoder.encode(UUID.randomUUID().toString())).
                        providerId(providerId).
                        provider(PROVIDER).build()))





                );


    }



}
