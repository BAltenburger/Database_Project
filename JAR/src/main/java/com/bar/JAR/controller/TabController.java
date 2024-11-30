/** packages and imports should be updated */

package main.java.com.bar.JAR.controller;


import main.java.com.bar.JAR.model.Tab;
import main.java.com.bar.JAR.repository.TabRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TabController {

    @Autowired
    TabRepository tabRepository;

    @GetMapping("/tabs")
    public ResponseEntity<List<Tab>> getAllTabs(@RequestParam(required = false) String tabID) {
        try {
            List<Tab> tabs = new ArrayList<Tab>();

            if (tabID == null)
                tabRepository.findAll().forEach(tabs::add);
            else
                tabRepository.findByTabID(tabID).forEach(tabs::add);

            if (tabs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tabs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tabs/{Id}")
    public ResponseEntity<tab> getTabById(@PathVariable("Id") long id) {
        Optional<tab> tabData = tabRepository.findById(id);

        if (tabData.isPresent()) {
            return new ResponseEntity<>(tabData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } 

    @PostMapping("/tabs")
    public ResponseEntity<tab> createTab(@RequestBody Tab tab) {
        try {
            Tab _tab = tabRepository
                    .save(new tab(tab.gettabId(), tab.gettabStart(), tab.gettabEnd(), tab.gettabRestriction()
                            ,tab.getContactID(),tab.getVenueID(),tab.getAttendeeCount()));
            return new ResponseEntity<>(_tab, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tabs/{tabID}")
    public ResponseEntity<Tab> updatetab(@PathVariable("id") long id, @RequestBody Tab tab) {
        Optional<Tab> tabData = tabRepository.findById(id);

        if (tabData.isPresent()) {
            Tab _tab = tabData.get();
            _tab.setFirstName(tab.getFirstName());
            _tab.setLastName(tab.getLastName());
            _tab.setDob(tab.getDob());
            _tab.setStreet(tab.getStreet());
            _tab.setCity(tab.getCity());
            _tab.setState(tab.getState());
            _tab.setZipCode(tab.getZipCode());
            _tab.setPhoneNo(tab.getPhoneNo());
            return new ResponseEntity<>(tabRepository.save(_tab), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    
    @DeleteMapping("/tabs/{Id}")
    public ResponseEntity<HttpStatus> deletetab(@PathVariable("id") long id) {
        try {
            tabRepository= tab.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tabs")
    public ResponseEntity<HttpStatus> deleteAlltabs() {
        try {
            tabRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
