import java.util.Date;
import java.util.Random;

public class ImprovedQuickSort {

  public static void main(String[] args) {
    Random rng = new Random();

    // Regular Test
    Date start = new Date();
    int runder = 0;
    double tid;
    Date slutt = new Date();
    int[] test;
    int sum;
    do {
      test = new int[1000000];
      sum = 0;
      for (int i = 0; i < 1000000; i++) {
        test[i] = rng.nextInt(-1000000, 1000000);
        sum += test[i];
      }

      quicksort(test, 0, test.length - 1);
      if (!sortIsCorrect(test, sum)) {
        System.out.println("Sort is incorrect for the regular test, timing terminated.");
        break;
      }

      slutt = new Date();
      ++runder;
    } while (slutt.getTime() - start.getTime() < 10000);
    tid = ((double) (slutt.getTime() - start.getTime())) / runder;

    // Duplicate Test
    Date dupStart = new Date();
    int dupRunder = 0;
    double dupTid;
    Date dupSlutt = new Date();
    int dupSum;
    do {
      int[] duplicateTest = new int[1000000];
      dupSum = 0;
      for (int i = 0; i < 1000000; i++) {
        if (i % 2 == 0) {
          duplicateTest[i] = rng.nextInt(-1000000, 1000000);
        } else {
          duplicateTest[i] = 42; // Half of the numbers are 42
        }
        dupSum += duplicateTest[i];
      }

      quicksort(duplicateTest, 0, duplicateTest.length - 1);
      if (!sortIsCorrect(duplicateTest, dupSum)) {
        System.out.println("Sort is incorrect for the duplicate test, timing terminated.");
        break;
      }

      dupSlutt = new Date();
      ++dupRunder;
    } while (dupSlutt.getTime() - dupStart.getTime() < 10000);
    dupTid = ((double) (dupSlutt.getTime() - dupStart.getTime())) / dupRunder;

    Date sortertStart = new Date();
    int sortertRunder = 0;
    double sortertTid;
    Date sortertSlutt = new Date();

    do {
      int[] sortedTest = new int[test.length];
      System.arraycopy(test, 0, sortedTest, 0, test.length);

      quicksort(sortedTest, 0, sortedTest.length - 1);
      if (!sortIsCorrect(sortedTest, sum)) {
        System.out.println("Sort is incorrect, timing terminated.");
        break;
      }

      sortertSlutt = new Date();
      ++sortertRunder;
    } while (sortertSlutt.getTime() - sortertStart.getTime() < 10000);

    sortertTid = ((double) (sortertSlutt.getTime() - sortertStart.getTime()) / sortertRunder);

    System.out.printf("Regular Test: %.5f ms per runde%n", tid);
    System.out.printf("Duplicate Test: %.5f ms per runde%n", dupTid);
    System.out.printf("Sorted Test: %.5f ms per runde%n", sortertTid);
  }

  private static boolean sortIsCorrect(int[] t, int sum) {
    int checkSum = t[0];
    for (int i = 1; i < t.length; i++) {
      if (t[i] < t[i-1]) {
        return false;
      }
      checkSum += t[i];
    }
    return checkSum == sum;
  }
  public static void quicksort(int[] t, int v, int h) {
    if (h - v > 199) {
      int delepos = splitt(t, v, h);
      quicksort(t, v, delepos - 1);
      quicksort(t, delepos + 1, h);
    } else innsettingssort(t, v, h);
  }

  public static void bytt(int[] t, int i, int j) {
    int k = t[j];
    t[j] = t[i];
    t[i] = k;
  }

  private static int median3sort(int[] t, int v, int h) {
    int m = (v + h) / 2;
    if (t[v] > t[m]) bytt(t, v, m);
    if (t[m] > t[h]) {
      bytt(t, m, h);
      if (t[v] > t[m]) bytt(t, v, m);
    }
    return m;
  }

  private static int splitt(int[] t, int v, int h) {
    int iv, ih;
    int m = median3sort(t, v, h);
    int dv = t[m];
    bytt(t, m, h - 1);
    for (iv = v, ih = h - 1;;) {
      while(t[++iv] < dv);
      while(t[--ih] > dv);
      if (iv >= ih) break;
      bytt(t, iv, ih);
    }
    bytt(t, iv, h - 1);
    return iv;
  }

  public static void innsettingssort(int[] t, int v, int h) {
    for (int j = v + 1; j <= h; ++j) {
      int bytt = t[j];

      int i = j - 1;
      while(i >= 0 && t[i] > bytt) {
        t[i + 1] = t[i];
        --i;
      }
      t[i + 1] = bytt;
    }
  }
}
