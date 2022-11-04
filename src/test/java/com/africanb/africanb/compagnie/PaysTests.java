package com.africanb.africanb.compagnie;

import com.africanb.africanb.dao.entity.compagnie.Pays;
import com.africanb.africanb.dao.repository.compagnieRepository.PaysRepository;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class PaysTests {

    @Autowired
    private PaysRepository paysRepository;

    @Test
    public void createPays(){
        //Informations
        Pays pays = new Pays();
        pays.setDesignation("Côte d'ivoire");
        pays.setDescription("Pays de l'afrique de l'ouest");

        //Exeution de la methode
        Pays rtn=paysRepository.save(pays);

        //Verification du resultat
        assertNotNull(rtn);
        assertEquals(pays.getDesignation(),rtn.getDesignation());
    }
}
