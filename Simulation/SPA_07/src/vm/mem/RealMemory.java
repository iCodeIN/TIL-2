package vm.mem;

public class RealMemory {
	public int[] frame; // ������ ����
	public int size; // ������ ������
	public int now; // ���� ��ġ flag

	public RealMemory(final int FRAME_SIZE) {
		this.frame = new int[FRAME_SIZE];
		this.size = FRAME_SIZE;
		this.setFrameEmpty();
	}

	public void setFrameEmpty() {
		for (int i = 0; i < frame.length; i++) {
			frame[i] = -1;
		}
		this.now = 0;
	}

	public boolean isFullFrame() {
		if (now >= size) {
			return true;
		}
		return false;
	}
}