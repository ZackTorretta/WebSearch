
import java.io.BufferedWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

//<title>JAY-Z – Holy Grail Lyrics | Genius Lyrics</title>
//this element below is the artist's name "jay-z" in the web page. The header/title is the name of the song. The song is called "smile" as seen below in the 2nd line at the end. 
//<a ng-href="https://genius.com/artists/Jay-z" class="header_with_cover_art-primary_info-primary_artist" href="https://genius.com/artists/Jay-z">JAY-Z</a>
//<h1 class="header_with_cover_art-primary_info-title" ng-class="{'header_with_cover_art-primary_info-title--white': $ctrl.variants.white_title}">Smile</h1>
//<title>JAY-Z – Smile Lyrics | Genius Lyrics</title>
//https://jsoup.org/apidocs/org/jsoup/select/Selector.html
//could ONLY crawl pages with jay z in the url (only his songs). Then find the title
////<a ng-href="https://genius.com/artists/Jay-z" class="header_with_cover_art-primary_info-primary_artist" href="https://genius.com/artists/Jay-z">JAY-Z</a>
public class WebCrawler {

    private static final int MAX_DEPTH = 2;
    private final Set<URL> links;

    private WebCrawler(final URL startURL, int d, String ki) {
        this.links = new HashSet<>();
        crawl(initURLS(startURL), d, ki);
    }

    private static Set<URL> initURLS(final URL startURL) {
        final Set<URL> startURLS = new HashSet<>();
        startURLS.add(startURL);
        return startURLS;
    }

//    WebCrawler(String thefilenametxt, String utF8) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public void crawl(final Set<URL> URLS, int depth, String k) {
        final Set<URL> newURLS = new HashSet<>();
        URLS.removeAll(this.links);
        if (!URLS.isEmpty()) {

            try {
                this.links.addAll(URLS);
                for (final URL url : URLS) {

                    if (depth <= MAX_DEPTH) {
                        final Document document = Jsoup.connect(url.toString()).userAgent("Mozzila").ignoreContentType(true).ignoreHttpErrors(true).timeout(10000).get();
                                
                        final Elements linksOnPage = document.select("a[href*=https://genius.com/Jay-z]").select("a[href$=lyrics]");
                        depth = depth + 1;
                        for (Element link : linksOnPage) {

                            String urlText = link.attr("abs:href");
                            URL discoveredURL = new URL(urlText);

                            newURLS.add(discoveredURL);

                        }
                    }
                }

            } catch (final Exception | Error ignored) {
            }

            //iterate through the web crawled hash set to pick out the ones that the user searched for.
            crawl(newURLS, depth, k);

        }
        for (URL s : newURLS) {
            try {
                String test = "";
                String t = "";
                String o = "";
                String p = "";
                String ok = " ";
                test = s.toString();
                Document doc = Jsoup.connect(test).userAgent("Mozzila").ignoreContentType(true).ignoreHttpErrors(true).timeout(10000).get();
                String title = doc.title();

                o = title;
                String body = doc.body().text();
                for (int i = 0; i < o.length(); ++i) {
                    if (o.charAt(i) == ' ') {
                        t += '-';
                    } else {
                        t += o.charAt(i);
                    }
                }
                System.out.println("Title : " + t);
                for (int j = 0; j < t.length(); ++j) {
                    if (t.charAt(j) == '-' || t.charAt(j) == '|') {
                        p += ' ';
                    } else {
                        p += t.charAt(j);
                    }
                }
                
                System.out.println("new Title : " + p);
                BufferedWriter writer = null;
//                String z = "C:\\Users\\Zack\\Desktop\\Indexed\\Data\\";
                String z = "C:\\Users\\Zack\\OneDrive\\Desktop\\Indexed\\Data\\";
                writer = new BufferedWriter(new FileWriter(z + p + ".txt"));
                writer.write(body);
                writer.newLine();
                writer.newLine();
                writer.write(test);
                writer.close();

            } catch (IOException ex) {
                Logger.getLogger(WebCrawler.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
//write the urls somewhere?

    public static void main(String[] args) throws IOException {
        //I send this 0 up to the main function. It is declared as "depth" variable. Where I can play with it up there for 20 links
        //The depth of web crawling helped me do this. I altered it in a way to show only the first 20 as specified. Since this is a beginner crawler.
        Scanner myObj = new Scanner(System.in);
//        System.out.println("Please enter song title");
//        String song = myObj.nextLine();
//         System.out.println("Song is: " + song);
        String song = "holy grail";
        final WebCrawler crawler = new WebCrawler(new URL("https://genius.com/artists/Jay-z"), 0, song);

    }

}
