package ru.otus;

import java.util.List;

public class EmulationOfBanknoteReceipt {

    public static void main(String[] args) {
        AtmMachine atmMachine = new AtmMachine();
        putBanknote(atmMachine);

        try {
            List<Banknote> banknoteList = atmMachine.getBanknote(Currency.RUR, 1500L);
        } catch (Exception exception) {
            System.out.println("asd");
        }


    }

    private static void putBanknote(AtmMachine atmMachine) {
        atmMachine.putBanknote(new Banknote(Currency.RUR, 50), 100L);
        atmMachine.putBanknote(new Banknote(Currency.RUR, 100), 200L);
        atmMachine.putBanknote(new Banknote(Currency.RUR, 200), 300L);
        atmMachine.putBanknote(new Banknote(Currency.USD, 50), 20L);
        atmMachine.putBanknote(new Banknote(Currency.USD, 100), 2L);
    }
}
