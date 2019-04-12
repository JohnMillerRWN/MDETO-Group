package BOE.events;

public class MyEventBus {

    private static MyEventBus instance;
    private static com.google.common.eventbus.EventBus bus;

    static {
        setInstance(new MyEventBus());
        bus = new com.google.common.eventbus.EventBus();
    }

    public MyEventBus() {

    }
    
    public static void register(Subscriber subscriber) {
        bus.register(subscriber);
    }
    
    public static void post(Postable event) {
        bus.post(event);
    }

	public static MyEventBus getInstance() {
		return instance;
	}

	public static void setInstance(MyEventBus instance) {
		MyEventBus.instance = instance;
	}
}