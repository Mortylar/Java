package logic;

public class BMPStore {

    private static final byte BYTE_SIZE = 8;

    private int height_;
    private int weight_;
    private int size_;
    private byte[] image_;

    public BMPStore() {
        height_ = 0;
        weight_ = 0;
        size_ = 0;
    }

    public BMPStore(int weight, int height) { addSize(weight, height); }

    public void addSize(int weight, int height) {
        height_ = height;
        weight_ = weight;
        size_ = height_ * weight_;
    }

    public void setImage(byte[] image) {
        int length = image.length;
        image_ = new byte[length];
        for (int i = 0; i < length - 1; i += 2) {
            image_[i] = image[length - i - 2];
            image_[i + 1] = image[length - i - 1];
        }
    }

    public int getHeight() { return height_; }

    public int getWeight() { return weight_; }

    public int getSize() { return size_; }

    public byte[] getImage() { return image_; }
}
