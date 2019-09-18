package kaiyi.app.xhapp.service.curriculum;

import kaiyi.app.xhapp.entity.curriculum.MediaLibrary;
import kaiyi.app.xhapp.service.InjectDao;
import kaiyi.puer.crypt.cipher.RSACipher;
import kaiyi.puer.db.orm.ServiceException;
import kaiyi.puer.db.query.OrderBy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;


@Service("mediaLibraryService")
public class MediaLibraryServiceImpl extends InjectDao<MediaLibrary> implements MediaLibraryService {
    private RSACipher cipher;
    @PostConstruct
    public void postConstruct(){
        try {
            cipher=new RSACipher("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCFH6RaeuUQnK1ecb0jQTN0JZkJYCh9AMouI8MItRmAg2wo0yo4F21ir//bfITpzmw7nPubzY1hHmvJs9OmsxWnAovwghnWqbXWsX5Dl8MHYxzKPtXOYDMMzd6M9Zq33gGG7AT4AbwsVH3kOdkOstmvQelocwkPflOELjfCSsY3RGWLs2uAeyr87N88aSr3+J1ze/EVOcKfk6+sWUyppYHC1Lzrk9JGMz++TRzGkaW83n9hmB11myeWldGiDNEBLfT6JhyOXg+m7hHxHe8S6R8kxjgsK0yvMO/VQ/DtExfSs+sHBdtAI1fUuGuJsyNDkFjhGb/8mczqlXgeEo2GvP4pAgMBAAECggEAaaijmezQwk6Yi81agRDO9fChdn+LoyttTRG1SsPyab3lqsFvUPXHK/zk5h/+nlPoM6h1S0PqYprykjTYWAbruJKc31djiq1IVg6qWJurf8F6qEsXB6Xy3sbHTLnjAuyB6cSKbQ1zfPvSr8H6NWBexmtaBjzHhDSPtOm598vBZzsKqJYPpNagM20vgndRgl33qfvCfnYqMZsszj+FyS2EDhYeQ7314ZOx+dh3lsH3SliwkY/2MRWk0ME7miRCAQASDu2vEb0aYTat4tMJepWGSu2QgXfAaryCyTv1IfM7dCoRYTlt8ih6nlKwyHXCZRPY7KHPTSUu1y7GPc0WQNuQAQKBgQDR3Sycb2RjjwuDCbNSca2x+iMuWGRQCTYwJpGHrnwCbxcPnoefRszrwlmlDAIujZt2mqFu3GQGzqwNtuGACVorC1gBcbnUlWFI6d6/7FwAq5ogRGWzK11PM2JNcfWumbLK2FYKE7iQ5Kif9SjpVzlGpiPnz6i4q45lv2asbwpVyQKBgQCiY6L1hPiEIasC+1Gpn6av51Yt8CIGHgQ1iN6V7XG4XDIAVBMU1zFQsGIY7HAOUH4KEL68IzJz+55snPniJeIGzsHU38pcx5ppnxAIvBfI4BKuZE5KrdAYnIhttVhTN2b+lNg3pHi7F68Fo6B1KjP19WArklfcbxLlbFtNnQEVYQKBgQDCOJrMvBqAOSZCQ3v91M8XY5OjlJJhr+TCcy9EDqsd5YnGHsUNFw0XM2qbYJ20T8GM5lZ4ruazlhmNJ69SO1WDeKAd1g5RO14piKdpn2kWBbpCGoq0YnGclsVnPwxMZlodeFFISjAJiFS2lMEkEmEjjSa1pm1TWxISAC0ZcDp0YQKBgHntI7MCxvG2scdIWfVpOOeEY9GUJoe/S9vCS5X4tVT+bFCQz87rEE0MUmtyQ2SMDUaTchKivsQU3cuhRYIGfaIs5Z5m9XMaL5c0Zf6Y86bQj1Y/XAV4YfHIvirZaVr6Eyv9KCcHJ9saGkC6rcSrDl6TgUTV2YBaKa7238rzopehAoGBAJECfM6x9mxwWv9VkK3UD8Ijbkdl0v+VP6hjJvLnDN9cGc2U4YYlXkGZsFnr5dIQiFsPYkK2V1J/pF29SnvqOLjYbfU+4Dx0YIn2uhF9inZu4NlU9GRte0F110C2PXSpSbCURiMkmRy7ZuPCE8rwl9LJMAYAShuTDWl0vs+VYgxA",
                    "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhR+kWnrlEJytXnG9I0EzdCWZCWAofQDKLiPDCLUZgINsKNMqOBdtYq//23yE6c5sO5z7m82NYR5rybPTprMVpwKL8IIZ1qm11rF+Q5fDB2Mcyj7VzmAzDM3ejPWat94BhuwE+AG8LFR95DnZDrLZr0HpaHMJD35ThC43wkrGN0Rli7NrgHsq/OzfPGkq9/idc3vxFTnCn5OvrFlMqaWBwtS865PSRjM/vk0cxpGlvN5/YZgddZsnlpXRogzRAS30+iYcjl4Ppu4R8R3vEukfJMY4LCtMrzDv1UPw7RMX0rPrBwXbQCNX1LhribMjQ5BY4Rm//JnM6pV4HhKNhrz+KQIDAQAB");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    @Override
    public OrderBy getDefaultOrderBy(String prefix) {
        return new OrderBy(prefix,"createTime",OrderBy.TYPE.DESC);
    }

    @Override
    public void newMediaLibrary(String title, String videoId) throws ServiceException {
        MediaLibrary lib=new MediaLibrary();
        lib.setCreateTime(new Date());
        lib.setName(title);
        lib.setVideoId(videoId);
        saveObject(lib);
    }
}
