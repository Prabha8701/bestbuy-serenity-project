package com.bestbuy.curdtest;

import com.bestbuy.bestbuyinfo.ProductSteps;
import com.bestbuy.testbase.ProductTestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class ProductCRUDTest extends ProductTestBase {
    static String name ="Duracel11"+ TestUtils.getRandomValue();
    static String type ="max";
    static String upc ="ABC";
    static String model ="AAA";
    static int price = 10;
    static int shipping = 20;
    static  String description ="asdf";
    static  String manufacturer = "asdfsd";

    static int productId;
    @Steps
    ProductSteps productSteps;

    @Title("This will create new product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct( name,type,upc,description,model,price,shipping,manufacturer);
        response.log().all().statusCode(201);
        //    productId=response.log().all().extract().path("id");
        System.out.println("created id "+productId);
    }
    @Title("Verify if the product was added to the application")
    @Test
    public void test002(){

        HashMap<String, Object> studentMap = productSteps.getProductInfoByName(price);
        // Map<String,?> studentMap = productSteps.getProductInfoByName(productId);
        System.out.println("name"+name);
        Assert.assertThat(studentMap, hasValue(price));
        productId = (int) studentMap.get("id");
        System.out.println("created new data"+productId);

    }
    @Title("Update the user information and verify the updated information")
    @Test
    public void test003() {
        String p1 = "data.findAll{it.price = ";
        String p2 = "}.get(0)";
        name = name+"-updated-battery";
        System.out.println("ID of product"+productId);
        productSteps.updateProduct(productId,name,type,upc,description,model,price,shipping,manufacturer)
                .log().all().statusCode(200);
        HashMap<String,?> storeMap = productSteps.getProductInfoByName(price);
        Assert.assertThat(storeMap, hasValue(price));
    }
    @Title("Delete the student and verify if the student is deleted!")
    @Test
    public void test004() {
        System.out.println("Id which wanted to delete"+productId);
        productSteps.deleteProduct(productId).statusCode(401);
        productSteps.getProductId(productId).statusCode(404);

    }


}
