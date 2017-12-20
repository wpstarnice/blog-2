package cn.zicla.blog.rest.preference;

import cn.zicla.blog.rest.base.BaseEntityController;
import cn.zicla.blog.rest.base.WebResult;
import cn.zicla.blog.rest.core.Feature;
import cn.zicla.blog.rest.core.FeatureType;
import cn.zicla.blog.rest.tank.TankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/preference")
public class PreferenceController extends BaseEntityController<Preference, PreferenceForm> {

    @Autowired
    PreferenceService preferenceService;

    @Autowired
    PreferenceDao preferenceDao;

    @Autowired
    TankService tankService;

    public PreferenceController() {
        super(Preference.class);
    }

    @Override
    @Feature(FeatureType.USER_MANAGE)
    public WebResult edit(@Valid PreferenceForm form) {

        Preference preference = preferenceService.fetch();
        form.update(preference, checkUser());

        preferenceDao.save(preference);

        return success(preference);
    }

    //获取。
    @RequestMapping("/fetch")
    @Feature(FeatureType.PUBLIC)
    public WebResult fetch() {

        Preference preference = preferenceService.fetch();

        preference.setLogoTank(tankService.find(preference.getLogoTankUuid()));
        preference.setFaviconTank(tankService.find(preference.getFaviconTankUuid()));

        return success(preference);
    }

}
