/**
 * Class representing application.
 * Runs GUI.
 */
public class Main {
    public static void main(String[] args) {
        var app = new AppController();
        /*
        Aby dodać nową operacje, algorytm
        1. Należy utworzyć nową klasę implementującą Operation, to znaczy posiada metodę execute, która
        przyjmuje BufferedImage i zwraca Buffered Image
        2. Dodać do klasy ImageOperations makro, które będzie stringiem przedstawiającym tą operację, jeżeli ma ona
        parametr to dodać makro dla Slidera
        3. W klasie AppController, w funkcji setParameterSetting dodać w odpowiednie miejsce nazwę odpowiedniego makra
        4. W AppController w getOperationByView dodać case odpowiadający danej funkcji
        5. W AppController w getOperationViewByName dodać odpowiedni przypadek do tworzenia obiektu OperationView,
        w tym jaki ma być np slider jeżeli potrzebny
         */
    }
}