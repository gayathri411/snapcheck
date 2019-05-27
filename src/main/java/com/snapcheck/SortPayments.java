package com.snapcheck;

import java.util.List;

public class SortPayments {
	public void insertionSort(List<Payment> paymentList) {
		int n = paymentList.size();
		for (int i = 1; i < n; ++i) {
			Payment key = paymentList.get(i);
			int j = i - 1;
			while (j >= 0 && compare(paymentList.get(j), key) > 0) {
				paymentList.set(j + 1, paymentList.get(j));
				j = j - 1;
			}
			paymentList.set(j + 1, key);
		}
	}

	public void printList(List<Payment> paymentList) {
		for (int i = 0; i < paymentList.size(); i++) {
			System.out.println(paymentList.get(i).getAmount() + " " + paymentList.get(i).getDate());
		}

	}

	public void quickSort(List<Payment> list, int start, int end) {
		int partition_index = partition(list, start, end);
		if (start < end - 1)
			quickSort(list, start, partition_index - 1);
		if (partition_index < end)
			quickSort(list, partition_index, end);
	}

	private static int partition(List<Payment> list, int start, int end) {
		int i = start, j = end;
		Payment tmp;
		int pivotIdx = start + (end - start) / 2;
		Payment pivot = list.get(pivotIdx);

		while (i <= j) {
			while (compare(list.get(i), pivot) < 0)
				i++;
			while (compare(list.get(j), pivot) > 0)
				j--;
			if (i <= j) {
				tmp = list.get(i);
				list.set(i++, list.get(j));
				list.set(j--, tmp);
			}
		}
		return i;
	}

	private static int compare(Payment payment1, Payment payment2) {
		return payment1.getDate().compareTo(payment2.getDate());

	}

}
