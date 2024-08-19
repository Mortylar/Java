package edu.school21.printer.logic;

public class BMPStore {

    private static final byte BYTE_SIZE = 8;

    private int height_;
    private int width_;
    private int size_;
    private byte[] image_;

    public BMPStore() {
        height_ = 0;
        width_ = 0;
        size_ = 0;
    }

    public BMPStore(int width, int height) { addSize(width, height); }

    public void addSize(int width, int height) {
        height_ = height;
        width_ = width;
        size_ = height_ * width_;
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

    public int getWidth() { return width_; }

    public int size() { return size_; }

    public byte[] getImage() { return image_; }
}
