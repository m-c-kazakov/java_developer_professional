package ru.otus;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class AtmMachineHelper {

    public static void getMoneyForUser(LinkedList<Map.Entry<Banknote, Long>> sortedBanknoteByNominal, Long needCountMoney, List<Banknote> banknoteList) {

        if (sortedBanknoteByNominal.isEmpty()) {
            if (banknoteList.isEmpty()) {
                throw new RuntimeException();
            }
            return;
        }

        Map.Entry<Banknote, Long> banknoteLongEntry = sortedBanknoteByNominal.pollLast();

        long nominal = banknoteLongEntry.getKey().nominal();
        Long countBanknote = banknoteLongEntry.getValue();
        if (nominal < needCountMoney) {
            long numberBanknoteNeedGet = needCountMoney / nominal;
            if (numberBanknoteNeedGet > countBanknote) {
                numberBanknoteNeedGet = countBanknote;
            }
            banknoteLongEntry.setValue(countBanknote - numberBanknoteNeedGet);

            long finalNumberBanknoteNeedGet = numberBanknoteNeedGet;
            banknoteList.addAll(Stream.iterate(0L, integer -> integer < finalNumberBanknoteNeedGet, integer -> integer + 1)
                    .map(aLong -> banknoteLongEntry.getKey()).toList());

            needCountMoney = needCountMoney - nominal * numberBanknoteNeedGet;
        }

        getMoneyForUser(sortedBanknoteByNominal, needCountMoney, banknoteList);

    }
}
