// 로컬 스토리지에 JSON 형태로 저장 / 불러오기 / 삭제 헬퍼
const storage = {
  set: (key, object) => {
    if (!localStorage) return;
    localStorage[key] =
      typeof object === 'string' ? object : JSON.stringify(object);
  },
  get: key => {
    if (!localStorage) return null;
    if (!localStorage[key]) {
      return null;
    }
    try {
      const parsed = JSON.parse(localStorage[key]);
      return parsed;
    } catch (e) {
      return localStorage[key];
    }
  },
  remove: key => {
    if (!localStorage) return null;
    if (localStorage[key]) {
      localStorage.removeItem(key);
    }
  },
};

export default storage;
