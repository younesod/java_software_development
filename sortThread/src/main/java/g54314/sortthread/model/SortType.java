package g54314.sortthread.model;

public enum SortType {
    BUBBLE("Tri à bulles"),
    MERGE("Tri fusion");

    private final String type;

    SortType(String type) {
        this.type=type;
    }
    public static SortType fromString(String type){
        for(var sort : SortType.values()){
            if(sort.toString().equalsIgnoreCase(type)){
                return sort;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}
