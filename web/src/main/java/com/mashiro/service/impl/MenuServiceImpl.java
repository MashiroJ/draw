package com.mashiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashiro.entity.Menu;
import com.mashiro.mapper.MenuMapper;
import com.mashiro.service.MenuService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author mashiro
 * @description 针对表【sys_menu(系统菜单表)】的数据库操作Service实现
 * @createDate 2024-09-29 11:38:42
 */
@Service
@Slf4j
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
        implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ObjectMapper objectMapper;

    private static final String MENU_LIST_KEY = "menu:list";
    // 设置缓存过期时间
    private static final long CACHE_TTL = 3600L;

    @Override
    public List<Menu> queryListMenu() {
        // 从Redis获取缓存数据
        String cachedMenus = stringRedisTemplate.opsForValue().get(MENU_LIST_KEY);

        // 如果缓存为空，则查询数据库并更新缓存
        if (cachedMenus == null || cachedMenus.isEmpty()) {
            List<Menu> menuList = menuMapper.selectList(null);

            try {
                // 使用Jackson序列化对象
                String menuJson = objectMapper.writeValueAsString(menuList);

                // 设置缓存并添加过期时间
                stringRedisTemplate.opsForValue().set(MENU_LIST_KEY, menuJson, CACHE_TTL, TimeUnit.SECONDS);

                return menuList; // 直接返回从数据库查询的菜单列表
            } catch (JsonProcessingException e) {
                log.error("菜单列表序列化失败", e);
                return menuList; // 即使序列化失败也返回查询结果
            }
        }

        // 反序列化缓存的JSON字符串为List<Menu>对象
        try {
            return objectMapper.readValue(cachedMenus,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Menu.class));
        } catch (JsonProcessingException e) {
            log.error("菜单列表反序列化失败", e);
            // 反序列化失败时回退到数据库查询
            return menuMapper.selectList(null);
        }
    }
}