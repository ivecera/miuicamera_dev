package org.jcodec.containers.mp4.boxes;

public class MovieBox extends NodeBox {
    public MovieBox(Header header) {
        super(header);
    }

    public static MovieBox createMovieBox() {
        return new MovieBox(new Header(fourcc()));
    }

    public static String fourcc() {
        return "moov";
    }

    private MovieHeaderBox getMovieHeader() {
        return (MovieHeaderBox) NodeBox.findFirst(this, MovieHeaderBox.class, "mvhd");
    }

    public int getTimescale() {
        return getMovieHeader().getTimescale();
    }

    public TrakBox[] getTracks() {
        return (TrakBox[]) NodeBox.findAll(this, TrakBox.class, "trak");
    }

    public boolean isPureRefMovie() {
        TrakBox[] tracks;
        boolean z = true;
        for (TrakBox trakBox : getTracks()) {
            z &= trakBox.isPureRef();
        }
        return z;
    }

    public long rescale(long j, long j2) {
        return (j * ((long) getTimescale())) / j2;
    }
}
