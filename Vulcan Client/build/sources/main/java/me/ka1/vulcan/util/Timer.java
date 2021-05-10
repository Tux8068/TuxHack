package me.ka1.vulcan.util;

public class Timer {
   // $FF: synthetic field

   private long time;

   private long current = System.currentTimeMillis();

   public void reset() {
      this.current = System.currentTimeMillis();
   }

   public boolean hasReached(long var1) {
      return System.currentTimeMillis() - this.current >= var1;
   }

   public boolean passed(double ms)
   {
      return System.currentTimeMillis() - this.time >= ms;
   }

   public boolean sleep(long var1) {
      if (this.time() >= var1) {
         this.reset();
         return true;
      } else {
         return false;
      }
   }

   public long getTimePassed() {
      return System.currentTimeMillis() - this.current;
   }

   public void setTime(long time)
   {
      this.time = time;
   }

   public long time() {
      return System.currentTimeMillis() - this.current;
   }

   public boolean hasReached(long var1, boolean var3) {
      if (var3) {
         this.reset();
      }

      return System.currentTimeMillis() - this.current >= var1;
   }
}
