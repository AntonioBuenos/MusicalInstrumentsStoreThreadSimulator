package by.smirnov.threadsstore.repo;

import by.smirnov.threadsstore.utils.Randomizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceListRepo {

    public static final Map<String, Double> priceList = new HashMap<>();

    static {
        priceList.put("Elixir strings Nanoweb Baritone 12-68", 12.0);
        priceList.put("Elixir strings Nanoweb SuperLight 9-46", 9.6);
        priceList.put("Elixir strings Nanoweb CustomLight 9-42", 9.6);
        priceList.put("Elixir strings Nanoweb Light 10-46", 9.6);
        priceList.put("Elixir strings Nanoweb Medium 11-49", 9.6);
        priceList.put("Elixir Bass strings Nanoweb 45-105 4-Strings", 20.0);
        priceList.put("Elixir Bass strings Nanoweb 45-120 5-Strings", 25.0);
        priceList.put("GHS strings Zakk Wylde signature 10-60", 6.0);
        priceList.put("GHS strings Zakk Wylde signature 11-70", 6.4);
        priceList.put("Vic Firth drumsticks 2B", 14.0);
        priceList.put("Vic Firth drumsticks 2BN", 14.1);
        priceList.put("Vic Firth drumsticks 5B", 14.1);
        priceList.put("Drummaster drumsticks 2B USA Hickory", 9.0);
        priceList.put("Drummaster drumsticks 2B USA Oak", 9.0);
        priceList.put("Drummaster drumsticks 5B USA Hickory", 9.0);
        priceList.put("Drummaster drumsticks 5B USA Oak", 9.0);
        priceList.put("Vic Firth Signature Series Kenny Aronoff", 17.0);
        priceList.put("Vic Firth Signature Series Terry Bozzio", 17.0);
        priceList.put("Vic Firth Signature Series JoJo Mayer", 17.0);
        priceList.put("Vic Firth Signature Series Nicko McBrain", 17.0);
        priceList.put("Vic Firth Signature Series Mike Terrana", 17.0);
        priceList.put("Vic Firth Signature Series Dave Weckl", 17.0);
        priceList.put("TAMA twin drum pedal Speed Cobra HP910LN", 1210.7);
        priceList.put("Zildjian 17\" I Trash Crash", 300.0);
        priceList.put("Zildjian 16\" Planet Z Crash", 300.0);
        priceList.put("Zildjian L80 Low Volume Hi-Hats", 400.0);
        priceList.put("Zildjian Custom Mastersound Hi-Hats", 500.0);
        priceList.put("Zildjian 20\" L80 Low Volume Ride", 300.0);
        priceList.put("Zildjian 18\" Uptown Ride", 300.0);
        priceList.put("DW twin drum pedal 5000", 2500.0);
        priceList.put("Ultex pick 1,2mm", 0.6);
        priceList.put("Ultex pick 1,0mm", 0.6);
        priceList.put("Ultex pick 0,8mm", 0.6);
        priceList.put("Tortex pick 1,0mm", 0.6);
        priceList.put("Tortex pick 0,9mm", 0.6);
        priceList.put("Gibson LesPaul Classic, black", 2700.0);
        priceList.put("Gibson LesPaul Custom, sunburst", 4100.0);
        priceList.put("Gibson Flying V Standard, white", 1900.0);
        priceList.put("Washburn Nuno Bettencourt 4N USA", 3200.0);
        priceList.put("PRS Orianthi", 3600.0);
        priceList.put("Marshall JCM-2000 TSL-100", 1600.0);
        priceList.put("Laney Ironheart IRT-Studio", 400.0);
    }

    public static String getRandomGoodName(){
        List<String> goodsPositions = new ArrayList<>(priceList.keySet());
        int randomGoodNumber = Randomizer.get(0, goodsPositions.size()-1);
        return goodsPositions.get(randomGoodNumber);
    }
}
