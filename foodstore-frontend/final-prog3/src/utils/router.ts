export function navigate(page: string) {
  window.location.hash = page;
}

export function getRoute() {
  return window.location.hash.replace("#", "") || "login";
}