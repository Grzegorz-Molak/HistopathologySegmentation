# Program do przetwarzania obrazów histopatologicznych

## Założenia

Program powinien mieć interfejs graficzny umożliwiający: 
* Możliwość wyboru algorytmu przed analizą obrazu 
* Wstępne przetwarzanie obrazu (preprocessing)
* Ustawienie niezbędnych parametrów algorytmu 
* Importowanie obrazu i eksportowanie obrazu wynikowego 
* Wyświetlanie obrazu oryginalnego oraz wynikowego w czasie rzeczywistym 
* Implementacja algorytmów: 
* Progowania 
* Rozrostu regionów 
* K-średnich 
* Wododziałowego

## Budowa programu
Znajdujący się w toolbarze przycisk File umożliwia odczyt obrazu wejściowego i zapis obrazy wyjściowego.

Przetwarzanie obrazu składa się z kilku etapów:
* [preprocessingu](#preprocessing)
* [segmentacji](#segmentacja)
* [postprocessingu](#postprocessing)
### Preprocessing
W sekcji preprocessingu znajdują się następujące operacje:
* Konwersja na skalę szarości
* Blur
* Blur gaussowski 
* Dylatacja
* Erozja
Wszystkie operacje można łączyć ze sobą w różne kombinacje, przy czym każda operacja może być dodawana kilkakrotnie
### Segmentacja
Zgodnie z założeniami projektu program ma do zaoferowania wybrane algorytmy segmentacji:
* Progowanie z ręcznie ustawianym progiem
* Rozrost regionów
* Algorytm K-średnich
* Algorym wododziałowy
Jest także przewidziana możliwość użycia programu bez segmentacji poprzez wybór "pustego" algorytmu
### Postprocessing
W sekcji postprocessingu znajdują się następujące operacje:
* Konwersja na skalę szarości
* Blur
* Blur gaussowski
* Dylatacja
* Erozja
* Progowanie
* Krawędziowanie
* Nałożenie obrazu wyjściowego na wejściowe
* Odwrócenie kolorów obrazu 

## Diagramy klas
<!-- ![image](/diagrams/diagram1.png "Algorytmy") -->
<figure>
<img src="/diagrams/diagram1.png" alt="Algorytmy" style="width:100%">
<figcaption align = "center"><b>Algorytmy</b></figcaption>
</figure>

<figure>
<img src="/diagrams/diagram2.png" alt="Algorytmy" style="width:100%">
<figcaption align = "center"><b>Klasy potrzebne do implementacji algorytmu wododziałowego</b></figcaption>
</figure>
<!-- ![Klasy potrzebne do implementacji algorytmu wododziałowego](/diagrams/diagram2.png) -->
<figure>
<img src="/diagrams/diagram3.png" alt="Algorytmy" style="width:100%">
<figcaption align = "center"><b>Elementy GUI</b></figcaption>
</figure>
<!-- ![Elementy GUI](/diagrams/diagram3.png) -->
<figure>
<img src="/diagrams/diagram4.png" alt="Algorytmy" style="width:100%">
<figcaption align = "center"><b>Schemat działania programu</b></figcaption>
</figure>
<!-- ![GUI](/diagrams/diagram4.png) -->




## Proponowany sposób użycia programu

1. W sekcji [preprocessingu](#preprocessing) dodać konwersję na skalę szarości
2. Wybrać algorytm segmentacji
3. W sekcji [postprocessingu](#postprocessing) dodać krawędziowanie lub progowanie i nakładanie

## Opis algorytmów segmentacji
### Progowanie
Prosta konwersja obrazu w skali szarości na obraz binarny. Każdemu kolejnemu pikselowi przypisuje się wartość 0 oznaczającą kolor czarny jeżeli wartość piksela jest mniejsza od progu lub 255 oznaczającą kolor biały w przeciwnym wypadku
### Rozrost regionów
Segmentacja polega na wyodrębnianiu lokalnych jednorodnych obszarów. Zaczynając od pewnego punktu startowego sprawdza się wszystkich sąsiadów kolejno włączanych punktów. Jeżeli dany sąsiad(piksel) jest jednorodny z obszarem na podstawie pewnego kryterium(np. różnica w skali szarości) to zostaje on włączony do obszaru, zmieniając kolor na kolor punktu startowego. 
### Algorytm wododziałowy
Nazwa ta odnosi się metaforycznie do geologicznego działu wodnego, czyli podziału dorzecza, który oddziela sąsiednie zlewnie. Transformacja działu wodnego traktuje obraz, na którym działa, jak mapę topograficzną, gdzie jasność każdego punktu reprezentuje jego wysokość, i znajduje linie biegnące wzdłuż wierzchołków grzbietów.
### Algorytm K-średnich
Algorytm ma na celu podzielenie n pikseli na k skupień, w których każdy piksel należy do skupienia o najbliższym kolorze (centra skupień). Wynikiem działania algorytmu jest obraz sklądający się z K kolorów. Dzięki charakterystycznym kolorom obrazu histopatologicznego algorytm dobrze radzi z wyodrębnieniem komórek od tła.
