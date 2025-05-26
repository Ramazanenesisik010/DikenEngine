package me.ramazanenescik04.diken.resource;

public class ArrayBitmap implements IResource {
	private static final long serialVersionUID = 1L;
	
	public Bitmap[][] bitmap;

	public void setArray(Bitmap[][] array) {
		this.bitmap = array;
	}
	
	public Bitmap getBitmap(int x, int y) {
		return bitmap[x][y];
	}
	
	@Override
	public EnumResource getResourceType() {
		return EnumResource.IMAGE;
	}
}
