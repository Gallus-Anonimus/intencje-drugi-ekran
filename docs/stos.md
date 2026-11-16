
# Zadanie 3 - trzy ekrany i stos

## Opis

Aplikacja posiada trzy aktywności:

- MainActivity - ekran quizu.
- ResultActivity - ekran wyniku.
- AboutActivity - ekran informacji o aplikacji.

Przejścia pomiędzy ekranami realizowane są za pomocą Intent i metody startActivity().

## 1. Uruchomienie aplikacji

Na stosie znajduje się MainActivity.

```text
┌─────────────────┐
│  MainActivity   │
└─────────────────┘
```

## 2. Przejście z ekranu 1 na ekran 2

Po udzieleniu odpowiedzi uruchamiany jest ResultActivity.

```text
┌─────────────────┐
│ ResultActivity  │ <- szczyt
├─────────────────┤
│  MainActivity   │
└─────────────────┘
```

MainActivity przechodzi do stanu zatrzymania.

## 3. Naciśnięcie Wstecz

ResultActivity zostaje zamknięty. Użytkownik wraca do quizu.

```text
┌─────────────────┐
│  MainActivity   │
└─────────────────┘
```

## 4. Przejście do ekranu O aplikacji

Z MainActivity uruchamiany jest AboutActivity.

```text
┌─────────────────┐
│  AboutActivity  │ <- szczyt
├─────────────────┤
│  MainActivity   │
└─────────────────┘
```

## 5. Naciśnięcie Wstecz

AboutActivity zostaje zamknięty.

```text
┌─────────────────┐
│  MainActivity   │
└─────────────────┘
```

## 6. Ponowne naciśnięcie Wstecz

MainActivity zostaje zamknięty i stos aktywności jest pusty.

```text
      PUSTY STOS
```

## Wnioski

Stos aktywności działa według zasady LIFO
(Last In, First Out).

Nowa aktywność jest umieszczana na szczycie stosu.
Przycisk Wstecz zamyka aktualną aktywność i przywraca
poprzednią.

## Logcat

Tutaj należy wkleić rzeczywisty fragment Logcatu
z emulatora, pokazujący wywołania metod:

- onCreate()
- onStop()
- onDestroy()
