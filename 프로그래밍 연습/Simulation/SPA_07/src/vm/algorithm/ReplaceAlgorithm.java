package vm.algorithm;

import java.util.Random;
import vm.mem.RealMemory;
import vm.mem.ReferString;

public class ReplaceAlgorithm {
	/* �ش� �����ӿ� value�� �ִ��� �˻��ϴ� ���� �Լ� */
	private static boolean searchFrame(RealMemory ram, int value) {
		for (int i = 0; i < ram.size; i++) {
			if (ram.frame[i] == value) {
				return true;
			}
			if (ram.frame[i] == -1) {
				return false;
			}
		}
		return false;
	}

	// Debug Function
	private static void showFrameStatus(String type, int now, RealMemory ram) {
		System.out.println(type + "| level " + now);
		for (int i = 0; i < ram.size; i++) {
			System.out.printf("%d\t", ram.frame[i]);
		}
		System.out.println();
	}

	/* Fisrt-in, First-out ������ ��ü �˰��� */
	public static int usingFIFO(RealMemory ram, ReferString rs) {
		int fault = 0;
		int last = 0;
		for (int i = 0; i < rs.size; i++) {
			if (searchFrame(ram, rs.strSet[i]) == false) {
				fault++;
				if (ram.isFullFrame() == true) {
					ram.frame[last++] = rs.strSet[i];
					if (last >= ram.size) {
						last = 0;
					}
				} else {
					ram.frame[ram.now++] = rs.strSet[i];
				}
			}
			// showFrameStatus("FIFO", i+1, ram);
		}
		ram.setFrameEmpty(); // frame ���� �ʱ�ȭ
		return fault;
	}

	/* Least Recently Used ������ ��ü �˰��� */
	public static int usingLRU(RealMemory ram, ReferString rs) {
		int fault = 0;
		int old = 210000000;
		int[] tb = new int[ram.size]; // ������ ��ü Time�� ����Ǵ� �迭 tb
		for (int i = 0; i < ram.size; i++) { // tb �迭 -1�� �ʱ�ȭ
			tb[i] = -1;
		}
		for (int i = 0; i < rs.size; i++) { // rs�� ������
			if (rs.mod == 'G') { // rs Ÿ���� locality�� �� ��ȿ�� �˻�
				if (rs.strSet[i] >= rs.avg * 2) {
					rs.strSet[i] = rs.avg;
				}
			}
			if (searchFrame(ram, rs.strSet[i]) == false) {
				fault++;
				if (ram.isFullFrame() == true) {
					old = 2100000000;	//INFINITY
					int oldi = 0;
					for (int j = 0; j < tb.length; j++) {	//�������� ������ ���� ���� �ε����� �˻�
						if (old > tb[j]) {
							old = tb[j];
							oldi = j;
						}
					}
					ram.frame[oldi] = rs.strSet[i];
					tb[oldi] = i;
				} else {
					ram.frame[ram.now] = rs.strSet[i];
					tb[ram.now++] = i;
				}
			} else {
				for (int j = 0; j < ram.size; j++) {
					if (ram.frame[j] == rs.strSet[i]) {	//�����ӿ� �����ϴ� ���� ��� tb ����
						tb[j] = i;
						break;
					}
				}
			}
			// showFrameStatus("LRU", i+1, ram);
		}
		ram.setFrameEmpty(); // frame ���� �ʱ�ȭ
		return fault;
	}

	/* Random ������ ��ü �˰��� */
	public static int usingRandom(RealMemory ram, ReferString rs, final int SEED) {
		int fault = 0;
		Random rnd = new Random(SEED); // ���� �ν��Ͻ� ����
		for (int i = 0; i < rs.size; i++) {
			if (searchFrame(ram, rs.strSet[i]) == false) {
				fault++;
				if (ram.isFullFrame() == true) {
					ram.frame[rnd.nextInt(ram.size)] = rs.strSet[i];
				} else {
					ram.frame[ram.now++] = rs.strSet[i];
				}
			}
			// showFrameStatus("RANDOM", i+1, ram);
		}
		ram.setFrameEmpty(); // frame ���� �ʱ�ȭ
		return fault;
	}

	/* Second-Chance ������ ��ü �˰��� */
	public static int usingSecond(RealMemory ram, ReferString rs) {
		int fault = 0;
		int last = 0;
		boolean[] mark = new boolean[ram.size];	//BooleanŸ���� Value Marking Table
		for (int i = 0; i < mark.length; i++) {
			mark[i] = false;
		}
		for (int i = 0; i < rs.size; i++) {
			for (int j = 0; j < ram.size; j++) {
				if (ram.frame[j] == -1) {	//���� �˻� Frame�� �� ���¶�� ����
					fault++;
					mark[ram.now] = true;
					ram.frame[ram.now++] = rs.strSet[i];
					break;
				}
				if (ram.frame[j] == rs.strSet[i]) {	//Frame���� ���� ã�� ��� mark ���̺��� true��
					mark[j] = true;
					break;
				}
				if (j == ram.size - 1) {
					fault++;
					while (mark[last % ram.size] == true) { // Frame�� ����ť�� ���
						mark[last % ram.size] = false;
						last++;
					}
					ram.frame[last % ram.size] = rs.strSet[i];
					mark[last % ram.size] = true;
					last++;
				}
			}
			// showFrameStatus("SECOND-CHANCE", i+1, ram);
		}
		ram.setFrameEmpty(); // frame ���� �ʱ�ȭ
		return fault;
	}
}