package android.shibedays.com.materialdesign;

/**
 * Data model for each row of the RecyclerView.
 */
class Sport {

    //Member variables representing the mTitle and information about the sport
    private String title;
    private String info;
    private final int imageResource;

    /**
     * Constructor for the Sport data model
     * @param mTitle The name if the sport.
     * @param info Information about the sport.
     */
    public Sport(String mTitle, String info, int imageResource) {
        this.title = mTitle;
        this.info = info;
        this.imageResource = imageResource;
    }

    /**
     * Gets the mTitle of the sport
     * @return The mTitle of the sport.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Gets the info about the sport
     * @return The info about the sport.
     */
    public String getInfo() {
        return info;
    }

    public int getImageResource(){
        return imageResource;
    }
}

