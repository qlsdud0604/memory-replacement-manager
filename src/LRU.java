import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/** LRU Algorithm */
class LRU extends JFrame {
	int numberOfFrame; // number of frame
	int numberOfInput; // number of reference string

	int frame[]; // array of frame
	int referenceString[]; // array of reference string

	int memory_layout[][];

	/** constructor */
	public LRU(int numberOfFrame, int numberOfInput, int[] frame, int[] referenceString) {
		super("LRU Algorithm");
		this.setVisible(true);
		this.setSize(1300, 300);
		this.setLocation(20, 500);

		this.numberOfFrame = numberOfFrame;
		this.numberOfInput = numberOfInput;

		this.frame = frame;
		this.referenceString = referenceString;

		this.memory_layout = new int[numberOfFrame][numberOfInput];
	}

	/** show information of Optimal Algorithm */
	public void showInformation() {
		for (int i = 0; i < numberOfFrame; i++)
			frame[i] = -1;

		int hit = 0;
		int fault = 0;
		boolean frameIsFull = false;
		int indexOfFrame = 0;

		for (int i = 0; i < numberOfInput; i++) {
			int searchPage = -1;

			/** in case of hit */
			for (int j = 0; j < numberOfFrame; j++) {
				if (frame[j] == referenceString[i]) {
					searchPage = j;
					hit++;

					break;
				}
			}

			/** in case of fault */
			if (searchPage == -1) {

				/** in case of frame is full */
				if (frameIsFull) {
					int longestPage[] = new int[numberOfFrame];
					boolean longestPage_check[] = new boolean[numberOfFrame];

					for (int j = i - 1; j >= 0; j--) {
						for (int k = 0; k < numberOfFrame; k++) {
							if ((frame[k] == referenceString[j]) && (longestPage_check[k] == false)) {
								longestPage[k] = j;
								longestPage_check[k] = true;
								break;
							}
						}
					}

					int min = longestPage[0];
					indexOfFrame = 0;

					for (int j = 0; j < numberOfFrame; j++) {
						if (longestPage[j] == 0)
							longestPage[j] = Integer.MIN_VALUE;
						if (longestPage[j] < min) {
							min = longestPage[j];
							indexOfFrame = j;
						}
					}
				}
				frame[indexOfFrame] = referenceString[i];
				fault++;

				/** in case of frame is not full */
				if (!frameIsFull) {
					indexOfFrame++;
					if (indexOfFrame == numberOfFrame) {
						frameIsFull = true;
						indexOfFrame = 0;
					}
				}
			}
			for (int j = 0; j < numberOfFrame; j++) {
				this.memory_layout[j][i] = frame[j];
			}
		}
		float ratio = (float) hit / (float) numberOfInput;
		System.out.println("--------------------------------------------------------");
		System.out.println("LRU ALGORITHM");
		System.out.println("--------------------------------------------------------");
		System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
		System.out.print(" " + hit);
		System.out.printf("%9d", fault);
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