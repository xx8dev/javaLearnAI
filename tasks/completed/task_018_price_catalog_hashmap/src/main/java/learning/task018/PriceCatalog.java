package learning.task018;



import java.util.HashMap;

public class PriceCatalog {
    private final HashMap<String, Integer> code = new HashMap<>();
    public PriceCatalog(){}
    public void setPrice(String productCode, int priceRubles){
        if(priceRubles > 1_000_000 || priceRubles < 0){
            throw new IllegalArgumentException();
        }
        if(productCode == null||productCode.isEmpty()){
            throw new IllegalArgumentException();
        }
        code.put(productCode,priceRubles);


    }

    public int getPrice(String productCode){
        if(productCode == null||productCode.isEmpty()){
            throw new IllegalArgumentException();
        }
        return code.getOrDefault(productCode,-1);
    }
    public boolean removePrice(String productCode){
        if(productCode == null||productCode.isEmpty()){
            throw new IllegalArgumentException();
        }

        Integer Price = code.remove(productCode);
        return Price != null;
    }
    public int getProductCount(){
        return code.size();
    }
}
