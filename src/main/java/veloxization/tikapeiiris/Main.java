
package veloxization.tikapeiiris;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

/**
 *
 * @author Veloxization
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        Class.forName("org.sqlite.JDBC");
        Database db = new Database("jdbc:sqlite:db/drinkkiarkisto.db");
        AnnosDao annosDao = new AnnosDao(db);
        RaakaAineDao raakaAineDao = new RaakaAineDao(db);
        AnnosRaakaAineDao annosRaakaAineDao = new AnnosRaakaAineDao(db);
        
        // Avaa etusivu
        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", annosDao.findAll());
            
            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());
        
        // Avaa sivu, jossa voi luoda ja muokata drinkkejä
        get("/drinkit", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("drinkit", annosDao.findAll());
            map.put("raakaaineet", raakaAineDao.findAll());
            
            return new ModelAndView(map, "drinkit");
        }, new ThymeleafTemplateEngine());
        
        // Lisää uusi (tyhjä) drinkki, jos kenttään on kirjoitettu jotain
        post("/drinkit/add", (req, res) -> {
            String nimi = req.queryParams("nimi");
            if (!nimi.isEmpty()) {
                annosDao.save(new Annos(annosDao.findAll().size() + 1, nimi));
            }
            
            res.redirect("/drinkit");
            return "";
        });
        
        // Lisää ainesosa olemassaolevaan drinkkiin, jos drinkkejä ja ainesosia on ja järjestys on asetettu
        post("/drinkit", (req, res) -> {
            int drinkkiId = 0;
            int ainesosaId = 0;
            int jarjestys = 0;
            if (req.queryParams("drinkki") != null && req.queryParams("ainesosa") != null && req.queryParams("jarjestys") != null) {
                drinkkiId = Integer.parseInt(req.queryParams("drinkki"));
                ainesosaId = Integer.parseInt(req.queryParams("ainesosa"));
                jarjestys = Integer.parseInt(req.queryParams("jarjestys"));
            }
            String maara = req.queryParams("maara");
            String ohje = req.queryParams("ohje");
            
            if (drinkkiId > 0 && ainesosaId > 0 && jarjestys > 0) {
                annosRaakaAineDao.save(new AnnosRaakaAine(ainesosaId, drinkkiId, jarjestys, maara, ohje));
            }
            
            res.redirect("/drinkit");
            return "";
        });
        
        // Tarkastele drinkkiä, jonka id = :id
        get("/drinkit/:id", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            map.put("raakaaineet", annosRaakaAineDao.getRaakaAineet(id));
            map.put("drinkki", annosDao.findOne(id));
            
            return new ModelAndView(map, "drinkki");
        }, new ThymeleafTemplateEngine());
        
        // Poista drinkki, jonka id = :id
        get("/drinkit/:id/delete", (req, res) -> {
            HashMap map = new HashMap<>();
            int id = Integer.parseInt(req.params(":id"));
            annosDao.delete(id);
            annosRaakaAineDao.delete(id);
            
            res.redirect("/drinkit");
            return "";
        });
        
        // Sivu, jolla voi tarkastella, lisätä ja poistaa ainesosia
        get("/ainekset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("raakaaineet", raakaAineDao.findAll());
            
            return new ModelAndView(map, "ainekset");
        }, new ThymeleafTemplateEngine());
        
        // Lisää uusi ainesosa, jos kenttä ei ole tyhjä
        post("/ainekset", (req, res) -> {
            String nimi = req.queryParams("nimi");
            if (!nimi.isEmpty()) {
                raakaAineDao.save(new RaakaAine(raakaAineDao.findAll().size() + 1, nimi));
            }
            
            res.redirect("/ainekset");
            return "";
        });
        
        // Poista ainesosa, jonka id = :id
        get("/ainekset/:id/delete", (req, res) -> {
            int id = Integer.parseInt(req.params(":id"));
            raakaAineDao.delete(id);
            
            res.redirect("/ainekset");
            return "";
        });
    }
}
