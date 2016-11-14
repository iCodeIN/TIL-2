package vm.mem;

import java.util.Arrays;
import java.util.Random;

public class ReferString {
	Random rnd; // Random ��ü
	public int[] strSet; // ���� rs ����Ʈ �������
	public int size; // rs ������
	public int range; // rs ����
	public int avg; // rs ���
	public char mod; // rs Ÿ��

	/* Reference String ������ */
	public ReferString(int RS_SIZE, int RANGE, int SEED, char MOD) {
		this.size = RS_SIZE;
		this.mod = MOD;
		this.rnd = new Random(SEED);
		this.strSet = new int[RS_SIZE];

		/* MOD=='D' : Non Locality || MOD=='G' : Locality(using Gaussian) */
		switch (MOD) {
		case 'D':
			this.range = RANGE;
			this.avg = -1;
			this.makeStringDefault(range, SEED, 0, strSet.length);
			break;
		case 'G':
			this.range = RANGE;
			this.avg = RANGE / 2;
			this.makeStringDefault(range, SEED, 0, strSet.length / 5);
			this.makeStringGaussian(avg, avg / 10, SEED, strSet.length / 5,
					strSet.length / 5 * 2);
			this.makeStringDefault(range, SEED, strSet.length / 5 * 2,
					strSet.length / 5 * 3);
			this.makeStringGaussian((int) (avg * 1.5), avg / 10, SEED,
					strSet.length / 5 * 3, strSet.length / 5 * 4);
			this.makeStringDefault(range, SEED, strSet.length / 5 * 4,
					strSet.length);
			break;
		default:
			System.out.println("Make ReferString MOD Exception : " + MOD);
			return;
		}
	}

	/* �Ͼ� ������ ����Ʈ ���� */
	public void makeStringDefault(int RANGE, int SEED, int st, int ed) {
		for (int i = st; i < ed; i++) {
			strSet[i] = rnd.nextInt(RANGE);
		}
	}

	/* ����þ� ���� ������ ����Ʈ ���� */
	private void makeStringGaussian(int RANGE, int STDEV, int SEED, int st,
			int ed) {
		for (int i = st; i < ed; i++) {
			double v1, v2, s, temp;
			do {
				v1 = 2 * rnd.nextDouble() - 1; // -1.0 ~ 1.0 ������ �Ͼ���� ����
				v2 = 2 * rnd.nextDouble() - 1; // -1.0 ~ 1.0 ������ �Ͼ���� ����
				s = (v1 * v1) + v2 * v2;
			} while (s >= 1 || s == 0);
			s = Math.sqrt((-2 * Math.log(s)) / s);
			temp = v1 * s;
			temp = (STDEV * temp) + (double) RANGE; // ��� ���� ǥ������ ���� ����� ����þ� ����
													// ����
			strSet[i] = (int) temp > 0 ? (int) temp : (int) temp * -1;
		}
	}

	/* ������ �׽�Ʈ �޼ҵ� */
	public void showLocality() {
		System.out.println(this.toString());
		int value = this.mod == 'G' ? this.avg * 2 : this.range;
		int[] cnt = new int[value];
		for (int i = 0; i < cnt.length; i++) {
			cnt[i] = 0;
		}
		for (int i = 0; i < this.strSet.length; i++) {
			cnt[strSet[i]]++;
		}
		for (int i = 0; i < cnt.length; i++) {
			System.out.printf("[%d] : ", i);
			for (int j = 0; j < cnt[i]; j++) {
				System.out.printf("*");
			}
			System.out.println();
		}

	}

	@Override
	public String toString() {
		return "ReferString [strSet=" + Arrays.toString(strSet) + "]";
	}
}
