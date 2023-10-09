import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.Iterator;
import java.util.concurrent.SubmissionPublisher;

public class Main {

    public static void main(String[] args) {

        CustomHashTable table = new CustomHashTable();

        table.set("nome", "Gabriel");
        table.set("idade", "20");
        table.set("prof", "aluno");
        table.set("curr", "hjona");
        table.set("vizi", "ren");
        table.set("subiu", "forte");

        // String nome = table.get("nome");

        System.out.println(table.get("nome"));
        System.out.println(table.get("idade"));
        System.out.println(table.get("prof"));
        System.out.println(table.get("curr"));
        System.out.println(table.get("vizi"));
        System.out.println(table.get("subiu"));
        System.out.println(table.get("none"));
        System.out.println(table.size());
        table.remove("idade");
        System.out.println(table.size());
        System.out.println(table.get("nome"));
        System.out.println(table.get("idade"));
        System.out.println(table.get("prof"));
        System.out.println(table.get("curr"));
        System.out.println(table.get("vizi"));
        System.out.println(table.get("subiu"));
        System.out.println(table.get("none"));

        String[] keys = table.keys();
        System.out.println(keys);

        Iterator<CustomHashTable.Node> it = table.iterator();
        while (it.hasNext()) {
            CustomHashTable.Node current = it.next();

            System.out.println(current);
        }

    }

    public static void testArray() {
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        Subscriber<Integer> subscription = new Subscriber<Integer>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                this.subscription = subscription;
                System.out.println("Subscribed");
                subscription.request(1); // Request the first item
            }

            @Override
            public void onNext(Integer item) {
                System.out.println(item);
                subscription.request(1); // Request the next item
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        };

        publisher.subscribe(subscription);

        publisher.submit(3);
        publisher.submit(5);
        publisher.close();

        CustomArray arr1 = new CustomArray();

        arr1.push("0");
        arr1.push("1");
        arr1.push("2");
        arr1.push("8");

        Object first = arr1.shift();

        CustomArray arr2 = new CustomArray();
        arr2.push("4");
        arr2.push("5");
        arr1.concat(arr2);

        CustomArray arr3 = arr1.shadow();
        arr1.push("99");
        arr3.push("88");

        System.out.println(arr1.equals(arr3));
        System.out.println(arr1);
        System.out.println(arr3);

    }

}
