// 全局JavaScript函数和工具

// API基础URL
const API_BASE_URL = '/api';

// 页面加载动画控制
function showPageLoader() {
    // 创建或获取加载动画元素
    let loader = document.getElementById('pageLoader');
    if (!loader) {
        loader = document.createElement('div');
        loader.id = 'pageLoader';
        loader.className = 'page-loader';
        loader.innerHTML = '<div class="loader-spinner"></div>';
        document.body.appendChild(loader);
    }
    
    // 显示加载动画
    setTimeout(() => {
        loader.classList.add('active');
    }, 10);
}

function hidePageLoader() {
    const loader = document.getElementById('pageLoader');
    if (loader) {
        loader.classList.remove('active');
        
        // 动画完成后移除元素
        setTimeout(() => {
            if (loader.parentNode) {
                loader.parentNode.removeChild(loader);
            }
        }, 300);
    }
}

// 页面过渡效果
function applyPageTransition(element) {
    if (!element) return;
    
    // 添加过渡类
    element.classList.add('page-transition');
    
    // 触发过渡
    setTimeout(() => {
        element.classList.add('active');
    }, 10);
}

// 修改原生页面跳转方法，添加加载动画
const originalLocationAssign = window.location.assign;
window.location.assign = function(url) {
    showPageLoader();
    setTimeout(() => {
        originalLocationAssign.call(this, url);
    }, 300);
};

// 修改原生页面替换方法，添加加载动画
const originalLocationReplace = window.location.replace;
window.location.replace = function(url) {
    showPageLoader();
    setTimeout(() => {
        originalLocationReplace.call(this, url);
    }, 300);
};

// DOM加载完成后执行
document.addEventListener('DOMContentLoaded', function() {
    // 初始化页面
    initPage();
    
    // 为所有内部链接添加点击事件，显示加载动画
    document.addEventListener('click', function(e) {
        const target = e.target.closest('a');
        if (target && target.href) {
            const href = target.getAttribute('href');
            
            // 只处理内部链接（不以http开头或以当前域名开头）
            if (href && !href.startsWith('http') && !href.startsWith('#') && !href.startsWith('javascript')) {
                // 阻止默认行为
                e.preventDefault();
                
                // 显示加载动画
                showPageLoader();
                
                // 延迟跳转
                setTimeout(() => {
                    window.location.href = href;
                }, 300);
            }
        }
    });
    
    // 为页面主要内容添加过渡效果
    const mainContent = document.querySelector('main') || document.querySelector('.container') || document.body;
    applyPageTransition(mainContent);
});

// 序列化表单数据
function serializeForm(form) {
    const formData = new FormData(form);
    const object = {};
    formData.forEach((value, key) => {
        object[key] = value;
    });
    return object;
}

// 初始化页面
function initPage() {
    // 设置导航栏活动状态
    setActiveNavigation();
    
    // 检查用户登录状态
    checkLoginStatus();
    
    // 为页面主要内容添加过渡效果
    const mainContent = document.querySelector('main') || document.querySelector('.container') || document.body;
    applyPageTransition(mainContent);
    
    // 初始化其他页面特定功能
    if (typeof pageInit === 'function') {
        pageInit();
    }
}

// 设置导航栏活动状态
function setActiveNavigation() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.navbar-nav .nav-link');
    
    navLinks.forEach(link => {
        // 移除所有活动状态
        link.classList.remove('active');
        // 设置当前页面的活动状态
        const href = link.getAttribute('href');
        if (href === currentPath || (currentPath === '/' && href === '/')) {
            link.classList.add('active');
        }
    });
}

// 检查session中的用户信息
async function checkSession() {
    try {
        // 通过检查用户信息API来判断session是否有效
        const response = await fetch('/api/user/info', {
            method: 'GET',
            credentials: 'include' // 确保发送cookie
        });
        
        if (response.ok) {
            const result = await response.json();
            if (result.code === 200 && result.data) {
                console.log('session验证成功，用户信息:', result.data);
                return result.data;
            }
        }
        
        console.log('session验证失败');
        return null;
    } catch (error) {
        console.error('检查session时出错:', error);
        return null;
    }
}

// 检查用户登录状态
async function checkLoginStatus() {
    const token = localStorage.getItem('token');
    const userMenu = document.querySelector('.user-menu');
    
    console.log('检查登录状态，token:', token ? '存在' : '不存在');
    
    // 首先检查session
    const sessionUser = await checkSession();
    
    if (sessionUser) {
        // session中有用户信息，直接更新UI
        updateUserMenu(sessionUser);
        return true;
    }
    
    if (token) {
        // 如果有token，获取用户信息
        try {
            const user = await fetchUserInfo();
            if (user) {
                updateUserMenu(user);
                return true;
            } else {
                // fetchUserInfo返回null，表示获取用户信息失败
                console.log('获取用户信息返回null，清除token');
                localStorage.removeItem('token');
                updateLoginMenu();
                return false;
            }
        } catch (error) {
            console.error('获取用户信息失败:', error);
            // 如果获取用户信息失败，清除token并显示登录/注册按钮
            localStorage.removeItem('token');
            updateLoginMenu();
            return false;
        }
    } else {
        // 如果没有token，显示登录/注册按钮
        updateLoginMenu();
        return false;
    }
}

// 更新登录菜单
function updateLoginMenu() {
    const userMenu = document.querySelector('.user-menu');
    if (userMenu) {
        userMenu.innerHTML = `
            <a href="/login" class="btn btn-login me-2">登录</a>
            <a href="/register" class="btn btn-register">注册</a>
        `;
    }
    
    // 如果当前在需要登录的页面，跳转到登录页
    const currentPath = window.location.pathname;
    if (currentPath.startsWith('/user/')) {
        window.location.href = '/login';
    }
}

// 获取用户信息
async function fetchUserInfo() {
    try {
        console.log('正在获取用户信息...');
        const response = await apiRequest('/user/info');
        console.log('获取用户信息成功:', response);
        
        // 更新token（如果返回了新的token）
        if (response.data && response.data.token) {
            const token = response.data.token;
            localStorage.setItem('token', token.startsWith('Bearer ') ? token : `Bearer ${token}`);
        }
        
        // 返回用户信息
        return response.data;
    } catch (error) {
        console.error('获取用户信息失败:', error);
        // 清除本地存储的token
        localStorage.removeItem('token');
        // 抛出错误而不是返回null，让调用方能够正确处理
        throw error;
    }
}

// 更新用户菜单
function updateUserMenu(user) {
    const userMenu = document.querySelector('.user-menu');
    if (!userMenu) return;
    
    userMenu.innerHTML = `
        <div class="dropdown">
            <button class="btn dropdown-toggle" type="button" id="userDropdown" data-bs-toggle="dropdown" aria-expanded="false">
                <i class="bi bi-person-circle me-1"></i>${user.nickname || user.username || '用户'}
            </button>
            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                <li><a class="dropdown-item" href="/user/profile"><i class="bi bi-person me-2"></i>个人中心</a></li>
                <li><a class="dropdown-item" href="/user/settings"><i class="bi bi-gear me-2"></i>账户设置</a></li>
                <li><hr class="dropdown-divider"></li>
                <li><a class="dropdown-item" href="#" id="logoutBtn"><i class="bi bi-box-arrow-right me-2"></i>退出登录</a></li>
            </ul>
        </div>
    `;
    
    // 添加退出登录事件监听
    document.getElementById('logoutBtn').addEventListener('click', function(e) {
        e.preventDefault();
        logout();
    });
}

// 退出登录
function logout() {
    // 显示加载动画
    showPageLoader();
    
    // 从localStorage中获取token
    const token = localStorage.getItem('token');
    
    // 调用后端logout API（这会清除session）
    apiRequest('/api/auth/logout', 'POST')
        .then(response => {
            console.log('退出登录响应:', response);
        })
        .catch(error => {
            console.error('退出登录请求失败:', error);
        })
        .finally(() => {
            // 无论请求成功与否，都清除本地token并跳转到登录页
            localStorage.removeItem('token');
            window.location.href = '/login';
        });
}

// 格式化日期
function formatDate(dateString) {
    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;
    
    // 小于1分钟
    if (diff < 60000) {
        return '刚刚';
    }
    
    // 小于1小时
    if (diff < 3600000) {
        return Math.floor(diff / 60000) + '分钟前';
    }
    
    // 小于1天
    if (diff < 86400000) {
        return Math.floor(diff / 3600000) + '小时前';
    }
    
    // 小于7天
    if (diff < 604800000) {
        return Math.floor(diff / 86400000) + '天前';
    }
    
    // 超过7天，显示具体日期
    return date.toLocaleDateString();
}

// 格式化日期时间为后端API期望的格式
function formatDateTime(dateTimeString) {
    if (!dateTimeString) return null;
    
    // 如果已经是完整的时间格式，直接返回
    if (dateTimeString.includes('T') && dateTimeString.includes(':')) {
        // 检查是否缺少秒部分
        if (dateTimeString.split(':').length === 2) {
            // 添加秒部分
            dateTimeString += ':00';
        }
        
        // 检查是否缺少时区信息
        if (!dateTimeString.includes('Z') && !dateTimeString.includes('+')) {
            // 添加时区信息（使用本地时区）
            const date = new Date(dateTimeString);
            return date.toISOString();
        }
        
        return dateTimeString;
    }
    
    // 如果是其他格式，尝试转换为标准格式
    const date = new Date(dateTimeString);
    if (isNaN(date.getTime())) {
        console.error('Invalid date format:', dateTimeString);
        return null;
    }
    
    return date.toISOString();
}

// 格式化数字（如：1000 -> 1k）
function formatNumber(num) {
    if (num >= 10000) {
        return (num / 10000).toFixed(1) + 'w';
    } else if (num >= 1000) {
        return (num / 1000).toFixed(1) + 'k';
    }
    return num.toString();
}

// 显示提示消息
function showMessage(message, type = 'info') {
    // 创建消息元素
    const messageEl = document.createElement('div');
    messageEl.className = `alert alert-${type} alert-dismissible fade show position-fixed`;
    messageEl.style.top = '20px';
    messageEl.style.right = '20px';
    messageEl.style.zIndex = '9999';
    messageEl.innerHTML = `
        ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    `;
    
    // 添加到页面
    document.body.appendChild(messageEl);
    
    // 3秒后自动移除
    setTimeout(() => {
        messageEl.remove();
    }, 3000);
}

// 显示确认对话框
function showConfirm(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// API请求封装
async function apiRequest(url, options = {}) {
    const defaultOptions = {
        headers: {}
    };
    
    // 如果用户已登录，添加认证头
    const token = localStorage.getItem('token');
    if (token) {
        // 确保token有Bearer前缀
        const authToken = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
        defaultOptions.headers['Authorization'] = authToken;
        console.log('添加认证头，token:', authToken.substring(0, 20) + '...');
    }
    
    // 如果不是FormData，设置默认的Content-Type为application/json
    if (!(options.body instanceof FormData)) {
        defaultOptions.headers['Content-Type'] = 'application/json';
    }
    
    // 合并选项
    const finalOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };
    
    console.log('发送API请求:', API_BASE_URL + url);
    
    try {
        const response = await fetch(`${API_BASE_URL}${url}`, finalOptions);
        
        // 检查响应状态
        if (!response.ok) {
            // 如果是401错误，清除token并重新检查登录状态
            if (response.status === 401) {
                console.log('收到401错误，清除token并重新检查登录状态');
                localStorage.removeItem('token');
                // 不在这里调用checkLoginStatus，避免无限递归
                updateLoginMenu();
            }
            
            const error = await response.json();
            throw new Error(error.message || '请求失败');
        }
        
        const result = await response.json();
        console.log('API请求成功:', result);
        return result;
    } catch (error) {
        console.error('API请求错误:', error);
        showMessage(error.message || '网络错误，请稍后重试', 'danger');
        throw error;
    }
}

// GET请求
function get(url, params = {}) {
    const queryString = new URLSearchParams(params).toString();
    const fullUrl = queryString ? `${url}?${queryString}` : url;
    return apiRequest(fullUrl);
}

// POST请求
function post(url, data = {}) {
    console.log('POST请求数据:', data);
    return apiRequest(url, {
        method: 'POST',
        body: JSON.stringify(data),
    });
}

// PUT请求
function put(url, data = {}) {
    return apiRequest(url, {
        method: 'PUT',
        body: JSON.stringify(data)
    });
}

// DELETE请求
function del(url) {
    return apiRequest(url, {
        method: 'DELETE'
    });
}

// 表单序列化
function serializeForm(form) {
    const formData = new FormData(form);
    const result = {};
    
    for (const [key, value] of formData.entries()) {
        // 特殊处理复选框，将字符串值转换为布尔值
        if (key === 'rememberMe') {
            result[key] = value === 'true';
        } else {
            result[key] = value;
        }
    }
    
    return result;
}

// 防抖函数
function debounce(func, wait) {
    let timeout;
    return function(...args) {
        clearTimeout(timeout);
        timeout = setTimeout(() => func.apply(this, args), wait);
    };
}

// 节流函数
function throttle(func, limit) {
    let inThrottle;
    return function(...args) {
        if (!inThrottle) {
            func.apply(this, args);
            inThrottle = true;
            setTimeout(() => inThrottle = false, limit);
        }
    };
}

// 图片懒加载
function lazyLoadImages() {
    const images = document.querySelectorAll('img[data-src]');
    
    const imageObserver = new IntersectionObserver((entries, observer) => {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                const img = entry.target;
                img.src = img.dataset.src;
                img.removeAttribute('data-src');
                observer.unobserve(img);
            }
        });
    });
    
    images.forEach(img => {
        imageObserver.observe(img);
    });
}

// 初始化图片懒加载
document.addEventListener('DOMContentLoaded', lazyLoadImages);

// 修改隐私设置
// 修改前
apiRequest('/api/user/notification-settings', {
    method: 'PUT',
    body: JSON.stringify(notificationSettings)
})

// 修改后
apiRequest('/user/notification-settings', {
    method: 'PUT',
    body: JSON.stringify(notificationSettings)
})

// 修改前
apiRequest('/api/user/send-phone-code', {
    method: 'POST',
    body: JSON.stringify({ phone: phoneNumber })
})

// 修改后
apiRequest('/user/send-phone-code', {
    method: 'POST',
    body: JSON.stringify({ phone: phoneNumber })
})

// 修改前
apiRequest('/api/user/bind-phone', {
    method: 'POST',
    body: JSON.stringify({
        phone: phoneNumber,
        code: verifyCode
    })
})

// 修改后
apiRequest('/user/bind-phone', {
    method: 'POST',
    body: JSON.stringify({
        phone: phoneNumber,
        code: verifyCode
    })
})

// 修改前
apiRequest('/api/user/privacy-settings', {
    method: 'PUT',
    body: JSON.stringify(privacySettings)
})

// 修改后
apiRequest('/user/privacy-settings', {
    method: 'PUT',
    body: JSON.stringify(privacySettings)
})