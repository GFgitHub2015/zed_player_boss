<%@   page contentType="image/jpeg"
    import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"  pageEncoding="UTF-8"%>
<%!Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }%>
<%-- 公共-验证码生成 --%>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
 	int width = 60, height = 21;
    BufferedImage image = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);
    Graphics g = image.getGraphics();
    Random random = new Random();
    g.setColor(new Color(255,255,255));
    g.fillRect(0, 0, width, height);
    g.setFont(new Font("Times   New   Roman", Font.PLAIN, 18));
	g.setColor(getRandColor(10, 53));
    for (int i = 0; i < 20; i++) {
        int x = random.nextInt(width)+3;
        int y = random.nextInt(height)+3;
        int xl = random.nextInt(2);
        int yl = random.nextInt(2);
        g.drawLine(x, y, x + xl, y + yl);
    }
	char c[] = new char[62];
 	for (int i = 97, j = 0; i < 123; i++, j++) {
        c[j] = (char) i;
    }
    for (int o = 65, p = 26; o < 91; o++, p++) {
        c[p] = (char) o;
    }
    for (int m = 48, n = 52; m < 58; m++, n++) {
        c[n] = (char) m;
    }
    String sRand = "";
    for (int i = 0; i < 4; i++) {
        int x = random.nextInt(10)+ 52;
        String rand = String.valueOf(c[x]);
        sRand += rand;
		g.setColor(new Color(20 + random.nextInt(110), 20 + random
        .nextInt(110), 20 + random.nextInt(110)));
        g.drawString(rand, 13 * i + 6, 19);
    }
    session.setAttribute("randCode", sRand);
    g.dispose();
    ImageIO.setUseCache(true);
    ImageIO.write(image, "JPEG", response.getOutputStream());
    out.clear();
    out = pageContext.pushBody();
%>

