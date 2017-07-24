package priv.lst.thinkinjava;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class NioTrainning {
	public static void main(String[] args) {
		intBuffer();
		try {
			program();
			byteToInt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void byteBuffer() {
		try {
			RandomAccessFile aFile = new RandomAccessFile("/Users/lishutao/code/java/test.txt", "rw");
			FileChannel inChannel = aFile.getChannel();

			ByteBuffer buf = ByteBuffer.allocate(1024);

			int bytesRead = inChannel.read(buf);
			while (bytesRead != -1) {
				System.out.println("\n-----------------------------------------");
				System.out.println("Read " + bytesRead);
				buf.flip();
				System.out.println(buf.limit());// 文本中的字符类型是一个字节的。
				while (buf.hasRemaining()) {
					System.out.print((char) buf.get());
				}

				buf.clear();
				bytesRead = inChannel.read(buf);
			}
			aFile.close();
			
			RandomAccessFile bFile = new RandomAccessFile("/Users/lishutao/code/java/test.txt", "rw");
			FileChannel outChannel = bFile.getChannel();
			//bFile.seek(bFile.length());从文件末尾开始写
			//PrintWriter（filename，true）构造器的第二个值也是从末尾写数据。

			buf.clear();
			for(int i = 0; i < 10; i++){
				buf.put((byte)('a' + i));
			}
			
			buf.flip();
			
			outChannel.write(buf);
			
			bFile.close();
		} catch (Exception e) {

		}
	}

	public static void byteToInt() {
		ByteBuffer bb = ByteBuffer.allocate(1024);
		IntBuffer ib = bb.asIntBuffer();// bb 和 ib共同操作这1024个字节空间。
		ib.put(new int[] { 1, 4, 6, 8, 10 });

		System.out.println(" " + bb.position() + " " + ib.position() + " " + bb.limit() + " " + ib.limit() + " "
				+ bb.capacity() + " " + ib.capacity());
		bb.rewind();
		// bb.filp(); 如果使用该方法，bb的posion变为0，limit变为0.也即清空了该bb。但是数据仍然在bb内。
		System.out.println(" " + bb.position() + " " + ib.position() + " " + bb.limit() + " " + ib.limit() + " "
				+ bb.capacity() + " " + ib.capacity());
		ib.flip();// ib可以使用flip，因为ib执行了put操作position已经变成了5.bb的position不会变。
		System.out.println(" " + bb.position() + " " + ib.position() + " " + bb.limit() + " " + ib.limit() + " "
				+ bb.capacity() + " " + ib.capacity());
		System.out.println(ib.get(0));
		System.out.println(bb.getInt());
	}

	public static void intBuffer() {
		// 分配新的int缓冲区，参数为缓冲区容量
		// 新缓冲区的当前位置将为零，其界限(限制位置)将为其容量。它将具有一个底层实现数组，其数组偏移量将为零。
		IntBuffer buffer = IntBuffer.allocate(8);
		System.out.println("capacity : " + buffer.capacity());
		for (int i = 0; i < buffer.capacity(); ++i) {
			int j = 2 * (i + 1);
			// 将给定整数写入此缓冲区的当前位置，当前位置递增
			buffer.put(j);
		}

		// 重设此缓冲区，将限制设置为当前位置，然后将当前位置设置为0
		buffer.flip();

		// 查看在当前位置和限制位置之间是否有元素
		while (buffer.hasRemaining()) {
			// 读取此缓冲区当前位置的整数，然后当前位置递增
			int j = buffer.get();
			System.out.print(j + "  ");
		}

	}

	public static void program() throws IOException {
		FileInputStream fin = new FileInputStream("/Users/lishutao/code/java/HelloWorld.java");

		BufferedInputStream bufferStream = new BufferedInputStream(fin);
		byte[] b = new byte[1024];
		bufferStream.read(b);
		for (byte bt : b) {
			System.out.print((char) bt);
		}
		System.out.println("************");
		fin.close();
		// 创建缓冲区
		FileInputStream fins = new FileInputStream("/Users/lishutao/code/java/HelloWorld.java");
		// 获取通道
		FileChannel fc = fins.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate(1024);

		// 读取数据到缓冲区
		long result = fc.read(buffer);
		// result = fc.read(buffer);
		System.out.println("------------");
		System.out.println(result);

		buffer.flip();

		while (buffer.remaining() > 0) {
			byte bx = buffer.get();
			System.out.print(((char) bx));
		}
		System.out.println("\n***********");
		buffer.flip();// 重置缓冲区，继续读一遍缓冲区。
		while (buffer.remaining() > 0) {
			byte bx = buffer.get();
			System.out.print((bx) + " ");
		}
		buffer.flip();
		System.out.println("\n------------");// 直接将输入通道与输出通道对接。
		FileChannel out = new FileOutputStream("/Users/lishutao/logs/a.txt").getChannel();
		System.out.println("out size is :" + out.size());
		fc.transferTo(0, fc.size(), out);
		System.out.println("out2 size is :" + out.size());
		
		/*
		 * 追加的方式向channel内写入数据。
		 */
		FileChannel out2 = new FileOutputStream("/Users/lishutao/logs/a.txt", true).getChannel();
		fc.transferTo(0, fc.size(), out2);
		fins.close();

		ByteBuffer bb = ByteBuffer.allocate(1024);
		bb.asCharBuffer().put("hello");
		bb.rewind();// 跟flip有区别
		System.out.println(bb.position() + " " + bb.limit());
		char cc;
		while ((cc = bb.getChar()) != 0) {
			System.out.println(cc + " ");
		}
		System.out.println(bb.position());
	}
}
