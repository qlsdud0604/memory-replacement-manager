import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/** LFU Algorithm */
class LFU extends JFrame {
	int numberOfFrame; // number of frame
	int numberOfInput; // number of reference string

	int frame[]; // array of frame
	int referenceString[]; // array of reference string

	int frameFrequency[]; // array of frame frequency
	int pageFrequency[]; // array of page frequency

	int memory_layout[][];

	/** constructor */
	public LFU(int numberOfFrame, int numberOfInput, int[] frame, int[] referenceString) {
		super("LFU Algorithm");
		this.setVisible(true);
		this.setSize(1300, 300);
		this.setLocation(20, 500);

		this.numberOfFrame = numberOfFrame;
		this.numberOfInput = numberOfInput;

		this.frame = frame;
		this.referenceString = referenceString;

		this.frameFrequency = new int[numberOfFrame];
		this.pageFrequency = new int[20];

		this.memory_layout = new int[numberOfFrame][numberOfInput];
	}

	/** show information of LFU Algorithm */
	public void showInformation() {
		for (int i = 0; i < numberOfFrame; i++) {
			frame[i] = -1;
			frameFrequency[i] = -1;
		}

		int hit = 0;
		int min = 0;
		int frameNum = 0;

		boolean check = true;

		for (int i = 0; i < numberOfInput; i++) {
			check = true;

			int currentPage = referenceString[i];

			for (int j = 0; j < numberOfFrame; j++) {
				if (frame[j] == currentPage) {
					check = false;
					hit++;
					pageFrequency[currentPage]++;
					break;
				}
			}
			if (check) {
				if (i >= numberOfFrame) {
					for (int j = 0; j < numberOfFrame; j++) {
						frameNum = frame[j];
						frameFrequency[j] = pageFrequency[frameNum];
					}
					min = frameFrequency[0];
					for (int j = 0; j < numberOfFrame; j++) {
						if (frameFrequency[j] < min) {
							min = frameFrequency[j];
						}
					}
					for (int j = 0; j < numberOfFrame; j++) {
						if (frameFrequency[j] == min) {
							pageFrequency[currentPage]++;
							frame[j] = currentPage;
							break;
						}
					}
				} else {
					frame[i] = currentPage;
					pageFrequency[currentPage]++;
				}
			}
			for (int j = 0; j < numberOfFrame; j++) {
				this.memory_layout[j][i] = frame[j];
			}
		}
		float ratio = (float) hit / (float) numberOfInput;
		System.out.println("--------------------------------------------------------");
		System.out.println("LFU ALGORITHM");
		System.out.println("--------------------------------------------------------");
		System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
		System.out.print(" " + hit);
		System.out.printf("%9d", numberOfInput - hit);
		System.out.printf("%13.2f", ratio);
		System.out.printf("%15.2f", 1 - ratio);
		System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
		System.out.println("(us)");
		System.out.println("--------------------------------------------------------");
	}

	/** draw process of LRU algorithm */
	public void paint(Graphics g) {
		super.paint(g);
		this.getContentPane().setBackground(Color.white);

		int currentPlace = 0;

		/** draw string */
		for (int i = 0; i < numberOfInput; i++) {
			currentPlace += 55;
			g.drawString("" + referenceString[i], 10 + currentPlace, 50);
		}

		/** draw rectangle */
		int currentHeight = 0;
		currentPlace = 0;
		for (int i = 0; i < numberOfInput; i++) {
			currentHeight = 0;
			currentPlace += 55;
			for (int j = 0; j < numberOfFrame; j++) {
				g.drawRect(2 + currentPlace, 52, 25, 30 + currentHeight);
				currentHeight += 30;
			}
		}

		/** draw string in rectangle */
		currentHeight = 0;
		currentPlace = 0;
		for (int i = 0; i < numberOfInput; i++) {
			currentHeight = 0;
			currentPlace += 55;
			for (int j = 0; j < numberOfFrame; j++) {
				if (memory_layout[j][i] == -1) {
					break;
				}
				g.drawString("" + memory_layout[j][i], 10 + currentPlace, 71 + currentHeight);
				currentHeight += 30;
			}
		}

		/** draw hit */
		currentPlace = 0;
		for (int i = 1; i < numberOfInput; i++) {
			currentHeight = 0;
			currentPlace += 55;
			for (int j = 0; j < numberOfFrame; j++) {
				if (memory_layout[j][i - 1] != memory_layout[j][i]) {
					break;
				} else if (j == numberOfFrame - 1) {
					g.drawString("HIT", 60 + currentPlace, 63 + 30 * numberOfFrame);
				}
			}
		}
	}
}