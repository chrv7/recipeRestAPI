package recipes;

class Clock {
    public static void main(String[] args) {
        Clock clock = new Clock(); // the time is 12:00
        clock.next();
    }

    int hours = 12;
    int minutes = 0;

    void next() {
        minutes++;
        if (minutes > 59) {
            hours++;
            minutes -= 60;
        }
        if (hours > 12) {
            hours = 0;
        }
    }
}
