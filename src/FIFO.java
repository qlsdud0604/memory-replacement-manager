import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

/** FIFO Algorithm */
class FIFO extends JFrame {
	int numberOfFrame; // number of frame
	int numberOfInput; // number of reference string

	int frame[]; // array of frame
	int referenceString[]; // array of reference string

	int memory_layout[][];

	/** constructor */
	public FIFO(int numberOfFrame, int numberOfInput, int[] frame, int[] referenceString) {
		super("FCFS Algorithm");
		this.setVisible(true);
		this.setSize(1300, 300);
		this.setLocation(20, 500);

		this.numberOfFrame = numberOfFrame;
		this.numberOfInput = numberOfInput;

		this.frame = frame;
		this.referenceString = referenceString;

		this.memory_layout = new int[numberOfFrame][numberOfInput];
	}

	/** show information of FIFO Algorithm */
	public void showInformation() {
		for (int i = 0; i < numberOfFrame; i++)
			frame[i] = -1;

		int hit = 0;
		int fault = 0;

		int indexOfFrame = 0;
		boolean check;

		for (int i = 0; i < numberOfInput; i++) {
			check = false;

			for (int j = 0; j < numberOfFrame; j++) {
				if (frame[j] == referenceString[i]) {
					check = true;

					hit += 1;
				}
			}

			if (check == false) {
				frame[indexOfFrame] = referenceString[i];
				indexOfFrame++;

				if (indexOfFrame >= numberOfFrame)
					indexOfFrame = 0;

				fault += 1;
			}
			for (int j = 0; j < numberOfFrame; j++) {
				this.memory_layout[j][i] = frame[j];
			}
		}
		float ratio = (float) hit / (float) numberOfInput;
		System.out.println("--------------------------------------------------------");
		System.out.println("FIFO ALGORITHM");
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

	/** draw process of FIFO algorithm */
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