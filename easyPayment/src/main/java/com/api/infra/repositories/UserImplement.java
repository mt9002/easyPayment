/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.infra.repositories;

import com.api.domain.interfaces.outgoing.IUserRepository;
import com.api.domain.interfaces.outgoing.jpaORM.UserORM;
import com.api.domain.services.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
public class UserImplement implements IUserRepository {

    private final UserORM userORM;

    @Autowired
    public UserImplement(UserORM userORM) {
        this.userORM = userORM;
    }

    @Override
    public Response getById(Long id) {

        try {
            var user = userORM.findById(id)
                    .orElseThrow(() -> new RuntimeException("ID inválido o no encontrado"));
            
            return new Response(
                    "User encontrado",
                    HttpStatus.OK.value(),
                    true,
                    user);
        } catch (Exception e) {
            return new Response(
                    "User no encontrado",
                    HttpStatus.BAD_REQUEST.value(),
                    false,
                    null);
        }

    }
}
