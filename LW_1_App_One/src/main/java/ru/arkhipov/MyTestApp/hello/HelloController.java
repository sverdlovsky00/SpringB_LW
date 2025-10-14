package ru.arkhipov.MyTestApp.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {

    // Создаем HashMap

    private ArrayList<String> arrayList = new ArrayList<>();
    private HashMap<Integer,String> hashMap = new HashMap<>();
    private int hashMapCounter = 1;

    @GetMapping("/hello")
    public String hello(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam String s){
        arrayList.add(s);
        return "Успешное добавление: " +s;
    }


    @GetMapping ("/show-array")
    public String showArrayList(){
        if (arrayList.isEmpty()){return "ArrayList пуст.";}
        return "Вывод элементов: " + String.join(", ", arrayList);
    }

    @GetMapping ("/update-map")
    public String updateHashMap(@RequestParam String s){
        hashMap.put(hashMapCounter ++,s);
        return "Успешное добавление в HashMap " + s + " с ключом " + (hashMapCounter);
    }

    @GetMapping("/show-map")
    public String showHashMap() {
        if (hashMap.isEmpty()) {
            return "HashMap пуст";
        }
        return "Элементы HashMap: " + hashMap.toString();}

    @GetMapping ("/show-all-length")
    public String showAllLength(){
        return String.format("В списке элементов: %d, в HashMap элементов %d",
                arrayList.size(), hashMap.size());
    }

}