package me.tux.tuxhack.util;

public class Timer {
   private long time = -1L;

   public boolean passedS(double s) {
      return this.passedMs((long) s * 1000L);
   }

   public boolean passedDms(double dms) {
      return this.passedMs((long) dms * 10L);
   }

   public boolean passedDs(double ds) {
      return this.passedMs((long) ds * 100L);
   }

   public void resetTimeSkipTo(long p_MS) {
      this.time = System.currentTimeMillis() + p_MS;
   }

   public boolean passedMs(long ms) {
      return this.passedNS(this.convertToNS(ms));
   }

   public void setMs(long ms) {
      this.time = System.nanoTime() - this.convertToNS(ms);
   }

   public boolean passedNS(long ns) {
      return System.nanoTime() - this.time >= ns;
   }

   public long getPassedTimeMs() {
      return this.getMs(System.nanoTime() - this.time);
   }

   public Timer reset() {
      this.time = System.nanoTime();
      return this;
   }

   public long getMs(long time) {
      return time / 1000000L;
   }

   public long convertToNS(long time) {
      return time * 1000000L;
   }

   public boolean sleep(final long time) {
      if (time() >= time) {
         reset();
         return true;
      }
      return false;
   }
      public long time() {
         return System.currentTimeMillis();
      }

   public boolean reach(final long time) {
      return this.time() >= time;
   }
}

