/*  프로젝트 명	: Drug_Crawling_Project
 *  개발 인원		: 팀장 임성현 외 3인 (양승협, 염동빈, 유봉환)
 *  시작 일자		: 2021 - 08 - 01
 *  수정 일자		: 2021 - 08 - 07
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.nio.charset.StandardCharsets;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Common_Use	// 모든 클래스는 Common_Use (공용함수) 클래스를 상속받는다고 간주하고 작성
{
	// test PATH
	static final String PATH = "./Resources/123.txt";
	// test DIRECTORY
	static final String DIR = "./Resources/tmp";
	// DRUG, DISEASE PATH
	static final String DRUG_INTERACTION_PATH = "./Resources/Drug_Interaction/Drug_Interaction.txt";
	static final String DISEASE_NAME_PATH = "./Resources/Disease_Info/Disease_name.txt";
	static final String DISEASE_INFO_PATH = "./Resources/Disease_Info/";
	// DRUG, DISEASE URL
	static final String drug_url = "https://www.health.kr/member/login.asp?return_url=/interaction/drug.asp?";
	static final String disease_find_url = "https://www.health.kr/researchInfo/disease.asp?inputField=&search_Term=a&paging_value=";
	static final String disease_url = "https://www.health.kr/researchInfo/disease.asp";
	// For Selenium
	static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
	static final String WEB_DRIVER_PATH = "C:/Users/profe/eclipse-workspace/chromedriver.exe";
	
	public static void main (String[] args)				// 전체를 실행하는 main method
	{
		//Drug_Interaction D = new Drug_Interaction();	// 생성자
		//D.Init_txt(DRUG_INTERACTION_PATH);				// 텍스트 파일 초기화
		//D.Get_Interactions();							// 상호작용 정보 가져옴
		//D.Save_Interactions();							// 텍스트 파일에 저장
		//D.Load_Interactions();							// 텍스트 파일에서 상호작용 내용 불러옴
		//D.Find_Interactions();							// 텍스트 파일에서 검색 (미구현)
		//D.Build_Interactions();							// 상호작용 내용을 JFrame에 담음 (JList로)
		
		Disease_Info D2 = new Disease_Info();			// 생성자
		D2.Save_DiseaseName();							// 질병명으로 텍스트 파일 저장
		D2.Load_DiseaseName();							// 한 텍스트 파일에 질병명 리스트 저장
		D2.Build_Disease();								// 질병 내용을 JFrame에 담음 (JList로)
	}
	

	// Initialize Iterator Pointer
	// ## Verified
    public static void Init_Iter(ListIterator L) 
    {
        while (L.hasPrevious())
            L.previous();
    }
    
    // String[] to ListIterator<String>
    // ## Verified
    public static ListIterator<String> Str2ListIter(String[] S) 
    {
    	
    	LinkedList<String> list = new LinkedList<String>();
    	ListIterator<String> L = list.listIterator();
		try 
		{	
			for(int i = 0; i < S.length; i++) 
				list.add(S[i]);
			L = list.listIterator();
		} catch (Exception e) {	System.out.println("Iterator로의 변환을 실패하였습니다"); }
		
		return L;
	}
	
    // Elements to ListIterator<Element>
    // ## Verified
	public static ListIterator<Element> Ele2ListIter(Elements E) 
	{
		try
		{
			return E.listIterator();
		}
		catch (Exception e) {System.out.println("Iterator로의 변환을 실패하였습니다"); return E.listIterator();}

	}
    
    // Check Iterator Generic
    // ## Verified
    public static String Check_Iter(ListIterator L)
    {
    	Init_Iter(L);
        String tmp = L.next().getClass().getSimpleName();
        Init_Iter(L);
        System.out.println("This ListIterator Generic is " + tmp);
        
        return tmp;
    }

    // Initialize Text File
    // ## Verified
    public static Boolean Init_txt(String url) 
    {
        try
        {
        	File f = new File(url);
        	if(f.delete())
        	{
        		//System.out.println(f.getName() + " : Deleted!");
        		//Save_txt("", url);
        		System.out.println(f.getName() + " is Initialized.");
        		return true;
        	}
        	else
        	{
        		Save_txt("", url);
        		System.out.println(f.getName() + " is Generated.");
        		return true;
        	}
        	
        } catch(Exception e) {e.printStackTrace(); return false;}
    }
    
 	// Load TextFile on ListIterator
    // ## Verified
    public static ListIterator<String> Load_txt(String File_URL) 
    {
    	ListIterator<String> L1;
    	List<String> listTest = new LinkedList<String>();
    	try 
    	{
             File f = new File(File_URL);
             BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(f), "utf-8"));

             String s;

             while((s = buf.readLine()) != null) 
            	 listTest.add(s);
                 
             buf.close();
    	 } catch (Exception e) {e.printStackTrace();}
    	 
    	 L1 = listTest.listIterator();
             
    	 while(L1.hasNext())
    		 System.out.println(L1.next());
             
         Init_Iter(L1);

         return L1;
    }
    
    // Save text textfile
    // ## Verified
    public static Boolean Save_txt(String txt, String url)
    {
    	//System.out.println("[Save Text...]");
        try 
        {
        	File f = new File(url);
        	
        	if(!f.exists())
        		f.createNewFile();
        		
        	BufferedWriter output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), StandardCharsets.UTF_8));
        	
        	output.write(txt);
        	output.close();
        	System.out.println(f.getName() + " is saved.");
            return true;

        } catch (IOException ioe) {ioe.getStackTrace(); return false;}

    }
    
 	// Check Directory
    // ## Verified
    public static Boolean Check_Dir(String path) 
    {
        File file = new File(path);
        
        if(!file.exists())
        {
            file.mkdirs();
            System.out.println("Directory is created!");
            return true;
        }
        else 
        {
        	System.out.println("Directory was created!");
        	return false; 
        }
    }
    
    // Build JTree by Using ListIterator on JFrame
	// ## Verified
    public static Boolean Build_Tree(JFrame JF, ListIterator L)
    {
        boolean result = true;
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("ROOT");        //루트폴더 1개 추가하려면 만들어야 됨.
        DefaultMutableTreeNode low1 = new DefaultMutableTreeNode("LOW1");        //루트폴더에서 하위폴더 1개 추가하려면 만들어야 됨. 
        root.add(low1);
        
        JTree JT = new JTree();
        DefaultTreeModel model = new DefaultTreeModel(root);
        JT.setModel(model);
        
        
        if(Check_Iter(L).equals("String")) 
        {
            System.out.println("\nString 형으로 받음.");
            ListIterator<String> tmpL = L;
            try 
            {
                while(tmpL.hasNext()) 
                {
                    DefaultMutableTreeNode node1 = new DefaultMutableTreeNode(tmpL.next());
                    low1.add(node1);
                }
                JT.setSize(270, 480);
                JF.add(JT);
                return true;
            } catch (Exception e) {System.out.println("JTree 삽입 실패"); return false;}
        }
        
        else if(Check_Iter(L).equals("Element")) 
        {
            System.out.println("\nString 형으로 받음.");
            ListIterator<Element> tmpL = L;
            try 
            {
                while(tmpL.hasNext()) 
                {
                    DefaultMutableTreeNode node2 = new DefaultMutableTreeNode(tmpL.next().text());
                    low1.add(node2);
                }
                JT.setSize(270, 480);
                JF.add(JT);
                return true;
            } catch (Exception e) {System.out.println("JTree 삽입 실패"); return false;}
        }
        
        else //Unknown Generic
        	return false;
        

    }

    // Build JList by Using ListIterator on JFrame
	// ## Verified
    public static JList Build_List(ListIterator L) 
    {
		
		JList jL = new JList(new DefaultListModel());
		DefaultListModel model = (DefaultListModel) jL.getModel();
		//jL.setBounds(10, 10, 270, 480);
		
		if(Check_Iter(L).equals("String")) 
		{
			//System.out.println("\nString 형으로 받음");
			ListIterator<String> tmpL = L;
			try 
			{
				while(tmpL.hasNext()) 
					model.addElement(tmpL.next());
				return jL;
			} catch (Exception e) {	System.out.println("JList 삽입 실패"); return null;}
		}
		
		else if(Check_Iter(L).equals("Element")) 
		{
			//System.out.println("\nElement 형으로 받음");
			ListIterator<Element> tmpL = L;
			try 
			{	
				while(tmpL.hasNext()) 
					model.addElement(tmpL.next().text());
				return jL;
			} catch (Exception e) {	System.out.println("JList 삽입 실패"); return null;}
		}
		else //Unknown Generic
			return null;
	}
}
	

class Drug_Interaction extends Common_Use {						// 약물 상호작용 클래스 (공용함수 클래스 상속)
	private WebDriver driver;									// 셀레니움 드라이버
	private WebElement webElement;								// 셀레니움 엘리먼트
	
	// 이부분에 약학정보원 계정정보를 저장해야 정상적으로 작동합니다
	public final String page_id = "id_example";				// 약학정보원 아이디
	public final String page_pw = "pw_example";				// 약학정보원 비밀번호
	
	public List<String> information = new LinkedList<String>();	// 성분1, 성분2, 내용 저장
	public static String find = "Doxycycline Hyclate Hydrate";	// 검색할 약물이름
	public static String shop = "###########################";	// 구분자
	
	ListIterator<String> L;										// 저장할 리스트 이터레이터
	JFrame frame = new JFrame("상호작용 목록");						// 프레임 지정
	JScrollPane Scroll = new JScrollPane();						// 스크롤 패널
	JList list;													// 리스트
	
	public Drug_Interaction() {									// 생성자는 슈퍼클래스 생성자 상속
		super();
	}
	
	boolean Get_Interactions() {								// 약물 상호작용 가져오기
		// Property setup
		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);		// 크롬드라이버 프로퍼티 설정
		// setup
		ChromeOptions options = new ChromeOptions();			// 크롬드라이버 설정
		//options.addArguments("headless");						// 크롬창 안보이게 하기
		driver = new ChromeDriver(options);						// 크롬드라이버에 설정한 것 적용
		
		try {
			driver.get(drug_url);								// 약학정보원 사이트로 접근
			Thread.sleep(1000);									// 딜레이 안줘도되지만 일단 넣음
			//System.out.println(driver.getPageSource());
			
			//driver.switchTo().frame(driver.findElement(By.id("login_pop")));
			// 약학정보원 로그인 하기
			webElement = driver.findElement(By.id("id"));
			webElement.sendKeys(page_id);
			
			webElement = driver.findElement(By.id("passwd"));
			webElement.sendKeys(page_pw);
			
			webElement = driver.findElement(By.className("search"));
			webElement.submit();
			
			// 검색 내용 찾기 (검색 내용은 find라는 String 변수에 임의로 값을 넣은 상태)
			webElement = driver.findElement(By.id("interaction_search_word"));
			webElement.sendKeys(find);
			
			webElement = driver.findElement(By.className("btn02"));
			webElement.click();
			
			webElement = driver.findElement(By.xpath("//*[@id=\"s0\"]/a")); // xpath 부분이 계속 바뀜
			webElement.click();												// 맨 처음 페이지에 가서 검색 시에는 0번째 리스트로 나오나
																			// 한번 이상 검색하고 나면 리스트 인덱스가 계속 바뀜 (이전 상황에 영향을 받음)
			
			webElement = driver.findElement(By.xpath("//*[@id=\"btns\"]/button"));
			webElement.click();

			// 검색내용 가져오기 (셀레니움의 WebElement 이용)
			List<WebElement> el1 = driver.findElements(By.className("ingred1"));	// 성분 1
			List<WebElement> el2 = driver.findElements(By.className("ingred2"));	// 성분 2
			List<WebElement> el3 = driver.findElements(By.className("info"));		// 내용
			for(int i = 0; i < el1.size(); i++) {									// 문자열 리스트의 하나의 원소로써 3개의 정보를 합쳐 저장함
				information.add("성분1 : " + el1.get(i).getText() + "\n" + "성분2 : " + el2.get(i).getText() + "\n" + el3.get(i).getText() + "\n" + shop + "\n");
			}
			
		} catch (Exception e) { e.printStackTrace();
								driver.quit();										// 예외 발생 시 크롬드라이버 프로세스 종료 (공간 낭비 줄임)
								return false; }
		driver.quit();																// 정상 수행 시 크롬드라이버 프로세스 종료
		return true;
	}
	
	boolean Save_Interactions() {				// 상호작용 내용 txt 파일에 저장
		boolean result = true;
		if(information.isEmpty()) {				// 우선적으로 저장할 내용이 있는지 확인 (information의 내용은 Get_Interactions() 함수에서 채워짐)
			System.out.println("저장할 내용이 없습니다");
			return false;
		}
		for(String S : information) {			// txt파일로 저장하는 과정
			//System.out.println(S);
			result = Save_txt(S, DRUG_INTERACTION_PATH);
			if(!result)								// result에는 Save_txt를 통한 true / false 값이 옴
				return false;						// result가 false라면 저장되지 않음으로 간주		
		}
		return true;							// 그 외에는 정상적인 작동으로 간주
	}
	
	boolean Load_Interactions() {				// txt 파일에서 상호작용 내용 꺼내옴
		L = Load_txt(DRUG_INTERACTION_PATH);	// L은 맨처음 선언시에는 null로 되어있음
		if(L == null)							// L에 내용이 채워지지 않는다면
			return false;						// 불러오기 실패
		return true;							// 그 외는 정상적인 작동으로 간주
	}
	
	boolean Find_Interactions() {				// 미안하다 이건 모르겠다..
		return true;
	}
	
	boolean Build_Interactions() {				// JList로 저장하여 프레임에 띄우는 함수
		frame.setBounds(100, 100, 700, 500);	// JFrame에 대한 기본적인 정보 (JFrame의 제목 및 생성자는 클래스 맨 상단에 위치)
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Scroll.setBounds(10, 10, 670, 480);		// JList의 내용이 범위 이상일 시 스크롤을 위한 스크롤 패널의 사이즈
		frame.add(Scroll);						// 프레임에 스크롤패널을 추가시킴
		//목록을 메모장에서 불러와서 JFrame에 넣음
		if(L == null) {							// L이 비어있다면 Load_Interactions()가 작동하지 않았음을 의미
			System.out.println("먼저 파일을 불러와주세요");
			return false;
		}
		list = Build_List(L);					// JList list에 Build_List를 통해 만들어진 JList를 담음
		if(list == null)						// 만약 비어있다면 Build_List의 동작이 실패했음을 의미
			return false;
		Scroll.setViewportView(list);			// 스크롤 패널은 list의 내용을 스크롤 가능하게 해줌
		return true;
	}
}

class Disease_Info extends Common_Use {
	
	private WebDriver driver;
	private WebElement webElement;
	private int end = 1;						// 질병 총 갯수
	private int index = 0;	
	private int item = 0;
	private String inform_url = "https://www.health.kr/researchInfo/";
	ListIterator<String> L;						// 저장할 리스트 이터레이터
	
	JFrame frame = new JFrame("질병 목록");		// 프레임 지정
	JScrollPane Scroll = new JScrollPane();		// 스크롤 패널
	JList list;									// 리스트
	String[] nameList = new String[400];		// 질병의 이름들을 각각 저장하기 위한 배열 (사이즈가 조금 낭비됨..ㅠ 동적할당 어떻게 해야하나 모르겠다)
	public Disease_Info() {						// 기본적인 생성자는 슈퍼클래스의 생성자를 따라감
		super();
		try {
			Document doc = Jsoup.connect(disease_url).get();			// Jsoup을 통한 파싱을 진행함 (질병 검색 페이지)
			String count = doc.select("span.count").text();				// 질병의 총 갯수를 확인하기 위함
			int first, last;
			first = count.indexOf("총 ");								// 문자열 substring을 위한 첫번째 인덱스 값
			last = count.indexOf(" 건");									// 문자열 substring을 위한 마지막 인덱스 값
			end = Integer.parseInt(count.substring(first + 2, last));	// 개수를 end 변수에 저장
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	boolean Save_DiseaseName() {																// 질병의 이름.txt 파일 저장 함수
		try {
			for(int i = 1; i <= end/10; i++) {
				String backPage = disease_find_url + i + "&setLine=10&searchMode=&HV_Scroll=1";	// 10개씩 보기 기준으로 페이지의 뒷부분 정리
				Document Home = Jsoup.connect(backPage).get();									// 해당 페이지를 스크랩
				Elements names = new Elements(Home.select("td.txtL"));							// 그 중 td클래스의 txtL 내용을 Elemets에 담음
			
				ListIterator<Element> disease_name = names.listIterator();						// ListIterator에 담음
				while(disease_name.hasNext()) {													// 각각의 원소마다
					String tmp = "";
					String tmpURL = "";
					disease_name.next();															// 분류 내용 (넘김)
					disease_name.next();															// 질환명 한글(넘김)
					Element tmpE = disease_name.next();												// 질환명 영어 내용의 원소를 Element에 담음
					tmp = tmpE.text();																// 이를 또한 String에 담음
					tmpURL = tmpE.select("a").attr("href");											// 그 중 a태그의 href 내용을 가져와서 해당 질병의 상세 내용이 담긴 링크를 땀
					
					Document doc = Jsoup.connect(inform_url + tmpURL).get();						// 그 링크를 통해 다시 Jsoup으로 스크래핑
					String inform = doc.select("table.infotable").text();							// 질환의 정보가 담긴 table 전채를 파싱
					
					inform = inform.replaceFirst("질환명 (한글)", "질환명 (한글) : ");					// 예쁘게 정리해주기
					inform = inform.replaceFirst("질환명 (영문)", "\n질환명 (영문) : ");
					inform = inform.replaceFirst("분류 ", "\n분류 : ");
					inform = inform.replaceFirst("상병코드 ", "\n상병코드 : ");
					inform = inform.replaceFirst("정의 ", "\n정의 : ");
					inform = inform.replaceFirst("원인 ", "\n원인 : ");
					inform = inform.replaceFirst("증상 ", "\n증상 : ");
					inform = inform.replaceFirst("치료 ", "\n치료 : ");
					inform = inform.replaceFirst("기타 ", "\n기타 : ");
					String newPATH = DISEASE_INFO_PATH + tmp + ".txt";								// 질병의 이름으로 텍스트파일을 저장하기 위한 경로
					System.out.println("Save " + index + " : " + tmp);								// 저장되는 순서와 질병의 이름 출력
					Init_txt(newPATH);																// 저장하기 전 미리 초기화
					Save_txt(inform, newPATH);														// 저장
					index++;
					
				}
			}	
		} catch (IOException e) { 
			e.printStackTrace();
			index = 0;
			return false; }
		index = 0;
		return true;
	}
	
	boolean Load_DiseaseName() {																// 질병의 이름만을 저장하는 TOTAL_DISEASE.txt 파일 저장 함수
		try {
			String DiseaseList = "";
			for(int i = 1; i <= end/10; i++) {
				String backPage = disease_find_url + i + "&setLine=10&searchMode=&HV_Scroll=1";		// 10개씩 보기 기준으로 페이지의 뒷부분 정리
				Elements e = new Elements(Jsoup.connect(backPage).get().select("td.txtL"));			// 해당 페이지 스크랩 및 td클래스의 txtL 내용 따옴
				ListIterator<Element> disease_name = e.listIterator();								// 따온 내용을 ListIterator에 담음
				while(disease_name.hasNext()) {														// 각각의 원소마다
					disease_name.next();
					disease_name.next();
					String tmp = disease_name.next().text();											// 질병이름 담기
					//System.out.println(index + ":" + tmp);											// 인덱스와 질병이름 매치 확인을 위함
					nameList[index++] = tmp;															// 이름 리스트에 담기 (이는 추후 JList의 마우스클릭 이벤트에 사용됨)
					DiseaseList = DiseaseList.concat(tmp + "\n");										// 문자열 다음 줄에 붙이기
				}
			}	
			index = 1;
			String newPATH = DISEASE_INFO_PATH + "1_TOTAL_DISEASE" + ".txt";					// TOTAL_DISEASE.txt 파일의 경로 지정
			Init_txt(newPATH);																	// 미리 초기화 해주고
			Save_txt(DiseaseList, newPATH);														// 저장함
			L = Load_txt(newPATH);																// 그리고 ListIterator L에 저장된 내용을 넣어줌 (불러오기)
		} catch (IOException e) { 
			e.printStackTrace();
			index = 1;
			return false; }
		index = 1;
		return true;
	}

	boolean Build_Disease() {																// JList로 불러온 프레임을 띄우고 더블클릭 시 해당 질병의 정보를 새 프레임에 띄우는 함수
		frame.setBounds(100, 100, 500, 500);												// 프레임 설정
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//목록을 메모장에서 불러와서 JFrame에 넣음
		if(L == null) {																		// L이 비어있다면 Load_Disease()가 되지 않았음을 의미
			System.out.println("먼저 파일을 불러와주세요");
			return false;
		}
		Scroll.setBounds(10, 10, 670, 480);													// 스크롤 패널 사이즈 설정
		frame.add(Scroll);																	// 스크롤 패널을 프레임에 추가
		list = Build_List(L);																// L을 갖고 JList를 생성
		if(list == null)																	// list가 비어있다면 Build_List()가 되지 않았음을 의미
			return false;
		list.addMouseListener(new MouseAdapter() {											// JList에 더블클릭 이벤트 추가
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					// 더블 클릭 시
					item = list.locationToIndex(e.getPoint());										// 클릭한 리스트의 원소 인덱스를 가져옴
					System.out.println("You cliked " + item + " : " + nameList[item]);				// 해당 인덱스에 해당하는 질병명을 출력
					JFrame tmpFrame = new JFrame("상세 정보");											// 새로운 프레임 생성
					JScrollPane tmpScroll = new JScrollPane();										// 새로운 스크롤 패널 생성
					
					tmpFrame.setBounds(300, 300, 500, 500);											// 프레임 셋업
					tmpScroll.setBounds(10, 10, 480, 480);											// 스크롤 패널 셋업
					tmpFrame.add(tmpScroll);														// 프레임에 스크롤 패널 추가
					String tmpPATH = DISEASE_INFO_PATH + nameList[item] + ".txt";					// 해당 질병의 정보가 담긴 텍스트파일 경로
					ListIterator tmpL = Load_txt(tmpPATH);											// 해당 텍스트 파일을 불러와 tmpL에 담음
					tmpScroll.setViewportView(Build_List(tmpL));									// tmpL의 내용을 스크롤패널안에 담아줌
					tmpFrame.setVisible(true);
				}
			}
		});
		Scroll.setViewportView(list);														// 이는 질병명 전체를 스크롤패널안에 넣음
		return true;
	}
}