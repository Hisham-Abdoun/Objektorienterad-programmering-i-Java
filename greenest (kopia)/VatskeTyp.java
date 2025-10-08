package greenest;


public enum VatskeTyp {
    KRANVATTEN("kranvatten"),
    MINERALVATTEN("mineralvatten"),
    PROTEINDRYCK("proteindryck");

    private final String niceName;

    VatskeTyp(String niceName) {
        this.niceName = niceName;
    }

    public String getNiceName() {
        return niceName;
    }
}
