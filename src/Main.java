import client.NetworkDataSource;
import common.Trade;
import common.sql.CurrentData;
import server.JDBCCurrentDataSource;
import server.JDBCUserDataSource;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 *
 *
 * @author Dylan Holmes-Brown
 */
public class Main {
    public static void main(String[] args) throws Exception {
        JDBCCurrentDataSource data = new JDBCCurrentDataSource();
        Date date = Date.valueOf("2021-06-08");
        Trade trade = new Trade("Buy", "Apple", "CPU", 4, 20, date);
        //data.addTrade(trade);
        System.out.println(data.getCurrentSize());
        List <Trade> list = new ArrayList<>(data.getBuySell("Buy"));
//        List <Trade> list2 = new ArrayList<>();
//        for (int i = 0; i < list.size(); i++) {
//            list2.add(list.get(i));
//        }
//        System.out.println(list2.get(0).getBuySell() + " " + list2.get(1).getBuySell());
    }
}
