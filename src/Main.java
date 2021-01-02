import java.util.Scanner;

/** Main class */
public class Main {
	static int numberOfFrame; // number of frame
	static int numberOfInput; // number of reference string

	static int frame[]; // array of frame
	static int referenceString[]; // array of reference string

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Please enter the number of frames : "); // put the number of frames
		numberOfFrame = scanner.nextInt();
		frame = new int[numberOfFrame];

		System.out.print("Please enter the number of inputs : "); // put the number of inputs
		numberOfInput = scanner.nextInt();
		referenceString = new int[numberOfInput];

		System.out.print("Pleses enter reference strings : "); // put the reference strings
		for (int i = 0; i < numberOfInput; i++)
			referenceString[i] = scanner.nextInt();
		System.out.println("--------------------------------------------------------");
		System.out.println("There are some Page Replacement Algorithm.");
		System.out.println("1. FIFO Algorithm");
		System.out.println("2. LFU Algorithm");
		System.out.println("3. MFU Algorithm");
		System.out.println("4. LRU Algorithm");
		System.out.println("5. Optimal Algorithm");
		System.out.println("6. Finish the Program");

		int choice;

		while (true) {
			System.out.println();
			System.out.print("Select Page Replacement Algorithm : ");
			choice = scanner.nextInt();

			switch (choice) {
			case 1:
				FIFO fifo = new FIFO(numberOfFrame, numberOfInput, frame, referenceString);
				fifo.showInformation();
				break;
			case 2:
				LFU lfu = new LFU(numberOfFrame, numberOfInput, frame, referenceString);
				lfu.showInformation();
				break;
			case 3:
				MFU mfu = new MFU(numberOfFrame, numberOfInput, frame, referenceString);
				mfu.showInformation();
				break;
			case 4:
				LRU lru = new LRU(numberOfFrame, numberOfInput, frame, referenceString);
				lru.showInformation();
				break;
			case 5:
				Optimal optimal = new Optimal(numberOfFrame, numberOfInput, frame, referenceString);
				optimal.showInformation();
				break;
			case 6:
				System.out.println("The program has ended.");
				return;
			default:
				System.out.println("Please enter correctly.");

			}
		}
	}
}