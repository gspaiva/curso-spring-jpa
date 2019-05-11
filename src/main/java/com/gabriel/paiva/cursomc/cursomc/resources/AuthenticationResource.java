package com.gabriel.paiva.cursomc.cursomc.resources;


import com.gabriel.paiva.cursomc.cursomc.security.JWTUtils;
import com.gabriel.paiva.cursomc.cursomc.security.UserSS;
import com.gabriel.paiva.cursomc.cursomc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationResource {

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "/refreshtoken", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response){
        UserSS userSS = UserService.authenticated();
        String token = jwtUtils.generateToken(userSS.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }
}
