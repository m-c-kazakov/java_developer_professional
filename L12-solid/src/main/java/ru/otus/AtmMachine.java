package ru.otus;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class AtmMachine {

    Map<Banknote, Long> cell = new HashMap<>();

    public void putBanknote(Banknote banknote, Long count) {
        cell.put(banknote, count);
    }

    public List<Banknote> getBanknote(Currency currency, long needCountMoney) {
        LinkedList<Map.Entry<Banknote, Long>> sortedBanknoteByNominal = cell.entrySet().stream()
                .filter(entry -> entry.getKey().currency() == currency)
                .sorted(Comparator.comparingInt(o -> o.getKey().nominal())).collect(Collectors.toCollection(LinkedList::new));
//        Collections.reverse(sortedBanknoteByNominal);

        List<Banknote> banknoteList = new ArrayList<>();
        AtmMachineHelper.getMoneyForUser(sortedBanknoteByNominal, needCountMoney, banknoteList);
        return banknoteList;
    }
}
