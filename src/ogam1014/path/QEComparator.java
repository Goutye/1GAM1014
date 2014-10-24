package ogam1014.path;

import java.util.Comparator;

public class QEComparator implements Comparator<QueueElement> {

	@Override
	public int compare(QueueElement o1, QueueElement o2) {
		int distanceThis = (int) Math.sqrt((o1.getTile().x - o1.getEnd().x)
				* (o1.getTile().x - o1.getEnd().x)
				+ (o1.getTile().y - o1.getEnd().y)
				* (o1.getTile().y - o1.getEnd().y));
		int distanceQe = (int) Math.sqrt((o2.getTile().x - o2.getEnd().x)
				* (o2.getTile().x - o2.getEnd().x)
				+ (o2.getTile().y - o2.getEnd().y)
				* (o2.getTile().y - o2.getEnd().y));
		if (distanceThis > distanceQe)
			return 1;
		if (distanceThis < distanceQe)
			return -1;
		else
			return 0;
	}

}
