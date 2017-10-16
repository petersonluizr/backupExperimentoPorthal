package br.com.porthal.experimento;


import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author douglas giordano
 */
@ApplicationPath("porthal")
public class Config extends ResourceConfig {

    public Config() {
        packages("br.com.porthal.experimento.resources");
    }
}